package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.request.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.*;
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

/**
 * Service métier pour la gestion des articles.
 *
 * Gère :
 * - L'enregistrement de nouveaux articles et commentaires
 * - La recherche des articles
 */
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;


    /**
     * Retourne la liste des articles dans l'ordre choisi par l'utilisateur.
     *
     * @param sort paramètre optionnel contenant le type de tri ascendant ou descendant
     * @return une liste l'objet Article
     * @throws UnknowSortException si le type de tri n'est pas reconnu
     */
    public List<Article> getAllArticles(@RequestParam(required = false) String sort) {

        //TODO update with jwt token
        //TODO update TU
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

    /**
     * Retourne l'article demandé par son Id.
     *
     * @param id identifiant de l'article à récupérer
     * @return l'objet Article
     * @throws SubjectNotFoundWithIdException si le thème de l'article n'est pas trouvé
     */
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundWithIdException(id));
    }

    /**
     * Crée et enregistre un nouvel article..
     *
     * @param dto DTO contenant (subjectId ,title ,content)
     * @return un message de confirmation
     * @throws SubjectNotFoundWithIdException si le thème de l'article n'est pas trouvé
     */
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