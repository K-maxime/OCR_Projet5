package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.services.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects/{id}/subscribe")
@Tag(name = "Subscription endpoints", description = "Endpoints for managing subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "Subscribe to a subject",
            description = "Subscribe to a subject")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "subscription with subject created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),  // TODO Temporaire avant JWT
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Subject not found"),
            @ApiResponse(responseCode = "400", description = "Subscription already exists"),

    })
    @PostMapping()
    public ResponseEntity<MessageResponse> subscribeToSubject(@PathVariable Long id) {
        return ResponseEntity.ok(this.subscriptionService.suscribeToSubject(id));
    }

    @Operation(summary = "Unsubscribe to a subject",
            description = "Unsubscribe to a subject")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unsubscription with subject created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),  // TODO Temporaire avant JWT
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Subject not found"),
            @ApiResponse(responseCode = "404", description = "subscription not found"),

    })
    @DeleteMapping()
    public ResponseEntity<MessageResponse> unsubscribeFromSubject(@PathVariable Long id) {
        return ResponseEntity.ok(this.subscriptionService.unsuscribeToSubject(id));
    }
}

