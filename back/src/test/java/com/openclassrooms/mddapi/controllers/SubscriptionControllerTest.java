package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SubscriptionControllerTest extends AbstractControllerIntegrationTest {

    private User defaultUser;
    private Subject javaSubject;

    @BeforeEach
    void setUp() {
        resetDatabase();
        defaultUser = createDefaultUser();
        javaSubject = createSubject("Java");
    }

    @Test
    void testSubscribeToSubject_WhenRequestIsValid_ThenReturn200() throws Exception {
        mockMvc.perform(post("/api/subjects/{id}/subscribe", javaSubject.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("subscription avec succes "));
    }

    @Test
    void testSubscribeToSubject_WhenSubjectDoesNotExist_ThenReturn404() throws Exception {
        mockMvc.perform(post("/api/subjects/{id}/subscribe", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Aucun subject trouve avec l'Id 999"));
    }

    @Test
    void testSubscribeToSubject_WhenSubscriptionAlreadyExists_ThenReturn409() throws Exception {
        createSubscription(defaultUser, javaSubject);

        mockMvc.perform(post("/api/subjects/{id}/subscribe", javaSubject.getId()))
                .andExpect(status().isConflict())
                .andExpect(content().string("Vous êtes déjà abonné à ce thème."));
    }

    @Test
    void testUnsubscribeFromSubject_WhenSubscriptionExists_ThenReturn200() throws Exception {
        createSubscription(defaultUser, javaSubject);

        mockMvc.perform(delete("/api/subjects/{id}/subscribe", javaSubject.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", containsString("succes")));
    }

    @Test
    void testUnsubscribeFromSubject_WhenSubjectDoesNotExist_ThenReturn404() throws Exception {
        mockMvc.perform(delete("/api/subjects/{id}/subscribe", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Aucun subject trouve avec l'Id 999"));
    }

    @Test
    void testUnsubscribeFromSubject_WhenSubscriptionDoesNotExist_ThenReturn404() throws Exception {
        mockMvc.perform(delete("/api/subjects/{id}/subscribe", javaSubject.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Aucun subscription trouve avec l'Id 1"));
    }
}
