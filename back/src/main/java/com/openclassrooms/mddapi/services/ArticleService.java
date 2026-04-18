package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.request.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.ArticleNotFoundWithIdException;
import com.openclassrooms.mddapi.exceptions.SubjectNotFoundWithIdException;
import com.openclassrooms.mddapi.exceptions.UnknowSortException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithIdException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;


    // Récupérer tous les articles
    public List<Article> getAllArticles(@RequestParam(required = false) String sort) {

        //TODO update with jwt token
        userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundWithIdException(1L));// verifie l'existance du user


        if (sort == null || sort.equals("desc")) {
            return articleRepository.findArticlesByUserSubscriptionsDesc(1L);
        } else if (sort.equals("asc")) {
            return articleRepository.findArticlesByUserSubscriptionsAsc(1L);
        } else {
            throw new UnknowSortException(sort);
        }
    }

    // Récupérer un article par ID
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundWithIdException(id));
    }

    // Créer un article
    public MessageResponse createArticle(CreateArticleRequestDto dto) {
        //TODO update with jwt token
        User author = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundWithIdException(1L));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new SubjectNotFoundWithIdException(dto.getSubjectId()));

        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setAuthor(author);
        article.setSubject(subject);

        articleRepository.save(article);

        return new MessageResponse("Article created successfully!");
    }
}