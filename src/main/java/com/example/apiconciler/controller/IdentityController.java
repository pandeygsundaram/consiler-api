package com.example.apiconciler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiconciler.dto.IdentifyRequest;
import com.example.apiconciler.dto.IdentifyResponse;
import com.example.apiconciler.service.IdentityService;

@RestController
@RequestMapping("/identify")
public class IdentityController {

    @Autowired
    private IdentityService service;

    @PostMapping
    public ResponseEntity<IdentifyResponse> identify(@RequestBody IdentifyRequest request) {
        IdentifyResponse res = service.identify(request);
        return ResponseEntity.ok(res);
    }
}
