package com.example.apiconciler;

import com.example.apiconciler.controller.IdentityController;
import com.example.apiconciler.dto.IdentifyRequest;
import com.example.apiconciler.dto.IdentifyResponse;
import com.example.apiconciler.service.IdentityService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = IdentityController.class)
public class IdentityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IdentityService identityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void sampleTest() {
        System.out.println("🚨 Controller test was found!");
    }

    @Test
    public void testIdentify_shouldReturnExpectedResponse() throws Exception {
        // Given: dummy request
        IdentifyRequest request = new IdentifyRequest();
        request.setEmail("alice@example.com");
        request.setPhoneNumber("1234567890");

        // And: dummy expected response
        IdentifyResponse response = new IdentifyResponse();
        response.setPrimaryContactId(1);
        response.setEmails(List.of("alice@example.com"));
        response.setPhoneNumbers(List.of("1234567890"));
        response.setSecondaryContactIds(List.of());

        // When: service is called, return mocked response
        Mockito.when(identityService.identify(any(IdentifyRequest.class)))
               .thenReturn(response);

        // Then: perform the request and assert results
        mockMvc.perform(post("/identify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.primaryContactId").value(1))
            .andExpect(jsonPath("$.emails[0]").value("alice@example.com"))
            .andExpect(jsonPath("$.phoneNumbers[0]").value("1234567890"))
            .andExpect(jsonPath("$.secondaryContactIds").isArray());
    }
}
