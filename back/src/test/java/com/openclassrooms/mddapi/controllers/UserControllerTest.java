package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.request.UpdateUserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerIntegrationTest {

    @BeforeEach
    void setUp() {
        resetDatabase();
        createDefaultUser();
    }

    @Test
    void testUpdateUser_WhenRequestIsValid_ThenReturn200() throws Exception {
        UpdateUserRequestDto request = new UpdateUserRequestDto("updated@test.com", "updated.user", "NewPassword123!");

        mockMvc.perform(put("/api/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User updated successfully!"));
    }

    @Test
    void testUpdateUser_WhenEmailIsInvalid_ThenReturn400() throws Exception {
        UpdateUserRequestDto request = new UpdateUserRequestDto("invalid-email", "updated.user", "NewPassword123!");

        mockMvc.perform(put("/api/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("email: Invalid email format"));
    }

    @Test
    void testUpdateUser_WhenPasswordIsInvalid_ThenReturn400() throws Exception {
        UpdateUserRequestDto request = new UpdateUserRequestDto("updated@test.com", "updated.user", "short");

        mockMvc.perform(put("/api/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void testUpdateUser_WhenUserDoesNotExist_ThenReturn404() throws Exception {
        resetDatabase();
        UpdateUserRequestDto request = new UpdateUserRequestDto("updated@test.com", "updated.user", "NewPassword123!");

        mockMvc.perform(put("/api/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Aucun utilisateur trouve avec l'Id 1"));
    }
}
