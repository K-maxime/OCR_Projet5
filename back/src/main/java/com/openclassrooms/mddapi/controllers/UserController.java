package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.request.RegisterRequestDto;
import com.openclassrooms.mddapi.dto.request.UpdateUserRequestDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controleur des endpoints utilisateur.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User endpoints", description = "Endpoints for managing User")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Update the current user informations",
            description = "Update the current user informations (username / email / password)")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateUserRequestDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),  // TODO Temporaire avant JWT
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (missing/empty fields or invalid lengths)"),

    })
    @PutMapping("/me")
    public ResponseEntity<MessageResponse> updateUser(@Valid @RequestBody UpdateUserRequestDto request) {
        return ResponseEntity.ok(userService.updateProfile(request.getEmail(), request.getUsername(), request.getPassword()));
    }
}
