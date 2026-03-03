package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final WebClient.Builder userServiceWebClient;

    public boolean validateUser(String userId){
        try{
            return userServiceWebClient.build().get()
                    .uri("/api/users/{userId}",userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

        } catch (WebClientResponseException e) {
            if(e.getStatusCode()== HttpStatus.NOT_FOUND){
                throw new RuntimeException("Use Not Found"+userId);
            } else if (e.getStatusCode()== HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("invalid "+userId);
            }
        }

        return false;
    }
}
