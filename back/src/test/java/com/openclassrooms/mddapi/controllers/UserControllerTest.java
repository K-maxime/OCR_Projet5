package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.request.UpdateUserRequestDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends AbstractControllerIntegrationTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        resetDatabase();
        createDefaultUser();
    }

    @Test
    void testUpdateUser_WhenRequestIsValid_ThenReturn200() throws Exception {
        List<User> users = userRepository.findAll();
        String token = jwtTokenProvider.generateToken(users.get(0).getEmail());


        UpdateUserRequestDto request = new UpdateUserRequestDto("updated@test.com", "updated.user", "NewPassword123!");

        mockMvc.perform(put("/api/users/me")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User updated successfully!"));
    }

    @Test
    void testUpdateUser_WhenEmailIsInvalid_ThenReturn400() throws Exception {
        List<User> users = userRepository.findAll();
        String token = jwtTokenProvider.generateToken(users.get(0).getEmail());
        UpdateUserRequestDto request = new UpdateUserRequestDto("invalid-email", "updated.user", "NewPassword123!");

        mockMvc.perform(put("/api/users/me")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("email: Invalid email format"));
    }

    @Test
    void testUpdateUser_WhenPasswordIsInvalid_ThenReturn400() throws Exception {
        List<User> users = userRepository.findAll();
        String token = jwtTokenProvider.generateToken(users.get(0).getEmail());
        UpdateUserRequestDto request = new UpdateUserRequestDto("updated@test.com", "updated.user", "short");

        mockMvc.perform(put("/api/users/me")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void testUpdateUser_WhenUserIsNotAuthenticated_ThenReturn403() throws Exception {
        UpdateUserRequestDto request = new UpdateUserRequestDto("updated@test.com", "updated.user", "NewPassword123!");

        mockMvc.perform(put("/api/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
}
