package com.openclassrooms.mddapi.config;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

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
        user1.setPassword("Password1!");

        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("user2@test.com");
        user2.setUsername("user2");
        user2.setPassword("Password2!");
        user2 = userRepository.save(user2);

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
