package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@Valid @RequestBody ActivityRequest request) {

        ActivityResponse response = activityService.trackActivity(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader("X-User-ID") String userId) {

        List<ActivityResponse> response = activityService.getUserActivities(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<List<ActivityResponse>> getActivity(@PathVariable String activityId){
        return ResponseEntity.ok(Collections.singletonList(activityService.getActivityById(activityId)));
    }
}