package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SubjectControllerTest extends AbstractControllerIntegrationTest {

    private User defaultUser;
    private Subject javaSubject;
    private Subject springSubject;

    @BeforeEach
    void setUp() {
        resetDatabase();
        defaultUser = createDefaultUser();
        javaSubject = createSubject("Java");
        springSubject = createSubject("Spring");
        createSubscription(defaultUser, javaSubject);
    }

    @Test
    void testGetSubjects_WhenUserExists_ThenReturn200() throws Exception {
        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Java"))
                .andExpect(jsonPath("$[0].subscribed").value(true))
                .andExpect(jsonPath("$[1].name").value("Spring"))
                .andExpect(jsonPath("$[1].subscribed").value(false));
    }

    @Test
    void testGetSubjects_WhenNoSubjectExists_ThenReturn200WithEmptyList() throws Exception {
        subscriptionRepository.deleteAll();
        subjectRepository.deleteAll();

        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testGetSubjects_WhenUserDoesNotExist_ThenReturn404() throws Exception {
        resetDatabase();
        createSubject("Java");

        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Aucun utilisateur trouve avec l'Id 1"));
    }
}
