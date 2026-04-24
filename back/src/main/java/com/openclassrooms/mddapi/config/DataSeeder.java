package com.openclassrooms.mddapi.config;

import com.openclassrooms.mddapi.models.*;
import com.openclassrooms.mddapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() > 0) {
            return;
        }

        // ================= SUBJECT =================
        Subject subject = new Subject();
        subject.setName("subject");
        subject.setDescription("Description subject1");
        subject = subjectRepository.save(subject);

        // ================= USERS =================
        User user1 = new User();
        user1.setEmail("user1@test.com");
        user1.setUsername("user1");
        user1.setPassword(passwordEncoder.encode("Password1!"));

        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("user2@test.com");
        user2.setUsername("user2");
        user2.setPassword(passwordEncoder.encode("Password2!"));
        user2 = userRepository.save(user2);

        // ================= SUBSCRIPTION =================
        Subscription sub = new Subscription();
        sub.setUser(user1);
        sub.setSubject(subject);
        subscriptionRepository.save(sub);
        sub = new Subscription();
        sub.setUser(user2);
        sub.setSubject(subject);
        subscriptionRepository.save(sub);


        // ================= ARTICLE =================
        Article article = new Article();
        article.setTitle("Article title1");
        article.setContent("Article content1");
        article.setSubject(subject);
        article.setAuthor(user1);

        article = articleRepository.save(article);

        // ================= COMMENT =================
        Comment comment = new Comment();
        comment.setContent("Comment 1");
        comment.setArticle(article);
        comment.setAuthor(user1);

        commentRepository.save(comment);
    }
}
