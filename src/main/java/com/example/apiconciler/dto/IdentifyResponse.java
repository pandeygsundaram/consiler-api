package com.example.apiconciler.dto;

import java.util.List;

public class IdentifyResponse {
    private Integer primaryContactId;
    private List<String> emails;
    private List<String> phoneNumbers;
    private List<Integer> secondaryContactIds;

    public IdentifyResponse(Integer primaryContactId, List<String> emails, List<String> phoneNumbers,
            List<Integer> secondaryContactIds) {
        this.primaryContactId = primaryContactId;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
        this.secondaryContactIds = secondaryContactIds;
    }

    // Default constructor
    public IdentifyResponse() {
    }

    // Getters
    public Integer getPrimaryContactId() {
        return primaryContactId;
    }

    public List<String> getEmails() {
        return emails;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<Integer> getSecondaryContactIds() {
        return secondaryContactIds;
    }

    // Setters
    public void setPrimaryContactId(Integer primaryContactId) {
        this.primaryContactId = primaryContactId;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setSecondaryContactIds(List<Integer> secondaryContactIds) {
        this.secondaryContactIds = secondaryContactIds;
    }

}
