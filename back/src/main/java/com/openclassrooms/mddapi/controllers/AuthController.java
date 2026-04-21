package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.request.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.request.CreateCommentRequestDto;
import com.openclassrooms.mddapi.dto.request.LoginRequestDto;
import com.openclassrooms.mddapi.dto.responses.LoginResponseDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.dto.request.RegisterRequestDto;
import com.openclassrooms.mddapi.dto.responses.UserDetailResponseDto;
import com.openclassrooms.mddapi.mapper.LoginMapper;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.services.AuthService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controleur des endpoints d'authentification.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentification endpoints", description = "Endpoints for managing authentification")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginMapper loginMapper;


    @Operation(summary = "Get the current user comment",
            description = "return the user details")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),  // TODO Temporaire avant JWT
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<UserDetailResponseDto> getAuthenticatedUser() {
        //TODO update with token jwt

        return ResponseEntity.ok().body(this.userMapper.toDto(userService.getProfile()));
    }

    @Operation(summary = "Register a new user",
            description = "Create and publish a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterRequestDto.class))),
            @ApiResponse(responseCode = "409", description = "The user already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (missing/empty fields or invalid lengths)"),

    })
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody RegisterRequestDto request) {
        authService.register(request.getUsername(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Operation(summary = "Login a user",
            description = "Login a user and return a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequestDto.class))),
            @ApiResponse(responseCode = "401", description = "The user already exists or the password is invalid"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (missing/empty fields or invalid lengths)"),
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok().body(this.loginMapper.toDto(authService.login(request.getLogin(), request.getPassword())));
    }

    @Operation(summary = "Logout the current user",
            description = "Logout the current user")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logout successfully")
    })
    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logoutUser() {
        return ResponseEntity.ok(this.authService.logoutUser());
    }
}
