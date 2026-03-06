package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId) {

        log.info("Calling User validation API for userId: {}", userId);
        try {
            Boolean response = userServiceWebClient
                    .get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            return Boolean.TRUE.equals(response);

        } catch (WebClientResponseException e) {

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("User Not Found: " + userId);
            }
            else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Invalid User ID: " + userId);
            }

            throw new RuntimeException("User service error: " + e.getMessage());
        }
    }
}