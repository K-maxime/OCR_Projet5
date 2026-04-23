package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.request.CreateArticleRequestDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleControllerTest extends AbstractControllerIntegrationTest {

    private User defaultUser;
    private Subject javaSubject;
    private Subject springSubject;
    private Article oldestArticle;
    private Article newestArticle;

    @BeforeEach
    void setUp() {
        resetDatabase();
        defaultUser = createDefaultUser();
        javaSubject = createSubject("Java");
        springSubject = createSubject("Spring");
        createSubscription(defaultUser, javaSubject);
        createSubscription(defaultUser, springSubject);
        oldestArticle = createArticle(javaSubject, defaultUser, "Older article", "Older content", LocalDateTime.of(2024, 1, 10, 10, 0));
        newestArticle = createArticle(springSubject, defaultUser, "Newer article", "Newer content", LocalDateTime.of(2024, 3, 10, 10, 0));
    }

    @Test
    void testGetArticles_WhenValidRequest_ThenReturn200() throws Exception {
        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(newestArticle.getId()))
                .andExpect(jsonPath("$[0].title").value("Newer article"))
                .andExpect(jsonPath("$[1].id").value(oldestArticle.getId()));
    }

    @Test
    void testGetArticles_WhenSortAsc_ThenReturnArticlesSortedAscending() throws Exception {
        mockMvc.perform(get("/api/articles").param("sort", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(oldestArticle.getId()))
                .andExpect(jsonPath("$[0].title").value("Older article"))
                .andExpect(jsonPath("$[1].id").value(newestArticle.getId()));
    }

    @Test
    void testGetArticles_WhenInvalidSort_ThenReturn400() throws Exception {
        mockMvc.perform(get("/api/articles").param("sort", "invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Type invalid n'est pas un type de trie connue veuillez utiliser 'desc' ou 'asc'"));
    }

    @Test
    void testGetArticle_WhenArticleExists_ThenReturn200() throws Exception {
        mockMvc.perform(get("/api/articles/{id}", oldestArticle.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(oldestArticle.getId()))
                .andExpect(jsonPath("$.title").value("Older article"))
                .andExpect(jsonPath("$.subject.name").value("Java"))
                .andExpect(jsonPath("$.author.username").value("john.doe"));
    }

    @Test
    void testGetArticle_WhenArticleDoesNotExist_ThenReturn404() throws Exception {
        mockMvc.perform(get("/api/articles/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Aucun article trouve avec l'Id 999"));
    }

    @Test
    void testCreateArticle_WhenRequestIsValid_ThenReturn200() throws Exception {
        CreateArticleRequestDto request = new CreateArticleRequestDto(javaSubject.getId(), "Fresh article", "Fresh content");

        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Article created successfully!"));
    }

    @Test
    void testCreateArticle_WhenTitleIsBlank_ThenReturn400() throws Exception {
        CreateArticleRequestDto request = new CreateArticleRequestDto(javaSubject.getId(), "", "Fresh content");

        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("title: Title cannot be empty"));
    }

    @Test
    void testCreateArticle_WhenContentIsBlank_ThenReturn400() throws Exception {
        CreateArticleRequestDto request = new CreateArticleRequestDto(javaSubject.getId(), "Fresh article", "");

        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("content: Content cannot be empty"));
    }

    @Test
    void testCreateArticle_WhenSubjectDoesNotExist_ThenReturn404() throws Exception {
        CreateArticleRequestDto request = new CreateArticleRequestDto(999L, "Fresh article", "Fresh content");

        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Aucun subject trouve avec l'Id 999"));
    }
}
