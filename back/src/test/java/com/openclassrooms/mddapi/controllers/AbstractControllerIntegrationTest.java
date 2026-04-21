package com.openclassrooms.mddapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
abstract class AbstractControllerIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected SubjectRepository subjectRepository;

    @Autowired
    protected ArticleRepository articleRepository;

    @Autowired
    protected SubscriptionRepository subscriptionRepository;

    @Autowired
    protected CommentRepository commentRepository;

    protected void resetDatabase() {
        commentRepository.deleteAll();
        articleRepository.deleteAll();
        subscriptionRepository.deleteAll();
        subjectRepository.deleteAll();
        userRepository.deleteAll();

        restartIdentity("comments");
        restartIdentity("articles");
        restartIdentity("subscription");
        restartIdentity("subjects");
        restartIdentity("users");
    }

    private void restartIdentity(String tableName) {
        jdbcTemplate.execute("ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1");
    }

    protected User createDefaultUser() {
        User user = new User();
        user.setEmail("john.doe@test.com");
        user.setUsername("john.doe");
        user.setPassword("Password123!");
        return userRepository.save(user);
    }

    protected Subject createSubject(String name) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setDescription(name + " description");
        return subjectRepository.save(subject);
    }

    protected Article createArticle(Subject subject, User author, String title, String content, LocalDateTime createdAt) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setSubject(subject);
        article.setAuthor(author);

        Article savedArticle = articleRepository.save(article);
        savedArticle.setCreatedAt(createdAt);
        return articleRepository.save(savedArticle);
    }

    protected Subscription createSubscription(User user, Subject subject) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubject(subject);
        subscription.setCreatedAt(LocalDateTime.now());
        return subscriptionRepository.save(subscription);
    }
}
