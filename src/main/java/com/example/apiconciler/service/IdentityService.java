package com.example.apiconciler.service;

import java.util.HashSet;
import java.util.List;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiconciler.dto.IdentifyRequest;
import com.example.apiconciler.dto.IdentifyResponse;
import com.example.apiconciler.repository.ContactRepository;
import com.example.apiconciler.model.*;;;

@Service
public class IdentityService {

    @Autowired
    private ContactRepository repo;

    public IdentifyResponse identify(IdentifyRequest req) {
        List<Contact> matches = repo.findByEmailOrPhoneNumber(req.getEmail(), req.getPhoneNumber());

        if (matches.isEmpty()) {
            // No match → create primary
            Contact contact = new Contact();
            contact.setEmail(req.getEmail());
            contact.setPhoneNumber(req.getPhoneNumber());
            contact.setLinkPrecedence("primary");
            repo.save(contact);
            return new IdentifyResponse(contact.getId(),
                    List.of(contact.getEmail()),
                    List.of(contact.getPhoneNumber()),
                    new ArrayList<>());
        }

        // Build identity cluster
        Contact primary = matches.stream()
            .filter(c -> "primary".equals(c.getLinkPrecedence()))
            .findFirst()
            .orElse(matches.get(0));

        Set<String> emails = new HashSet<>();
        Set<String> phones = new HashSet<>();
        List<Integer> secondaries = new ArrayList<>();

        for (Contact c : matches) {
            if (!Objects.equals(c.getId(), primary.getId())) {
                if ("primary".equals(c.getLinkPrecedence())) {
                    c.setLinkPrecedence("secondary");
                    c.setLinkedId(primary.getId());
                    repo.save(c);
                }
                secondaries.add(c.getId());
            }
            if (c.getEmail() != null) emails.add(c.getEmail());
            if (c.getPhoneNumber() != null) phones.add(c.getPhoneNumber());
        }

        // New contact if current info is not already present
        boolean alreadyExists = matches.stream().anyMatch(c ->
            Objects.equals(c.getEmail(), req.getEmail()) &&
            Objects.equals(c.getPhoneNumber(), req.getPhoneNumber()));

        if (!alreadyExists) {
            Contact secondary = new Contact();
            secondary.setEmail(req.getEmail());
            secondary.setPhoneNumber(req.getPhoneNumber());
            secondary.setLinkPrecedence("secondary");
            secondary.setLinkedId(primary.getId());
            repo.save(secondary);
            secondaries.add(secondary.getId());
            if (req.getEmail() != null) emails.add(req.getEmail());
            if (req.getPhoneNumber() != null) phones.add(req.getPhoneNumber());
        }

        return new IdentifyResponse(primary.getId(),
                new ArrayList<>(emails),
                new ArrayList<>(phones),
                secondaries);
    }
}

