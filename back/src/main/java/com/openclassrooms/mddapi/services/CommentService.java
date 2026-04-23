package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.request.CreateCommentRequestDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.ArticleNotFoundWithIdException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithIdException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service métier pour la gestion des commentaires
 *
 * Gère :
 * - La création des commentaires
 */
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Autowired
    private AuthenticationService authService;

    /**
     * Crée un nouveau commentaire pour un article donné.
     *
     * @param articleId L'ID de l'article à commenter
     * @param dto   Le contenu du commentaire
     * @return MessageResponse indiquant le succès de l'opération
     */
    public MessageResponse createComment(Long articleId, CreateCommentRequestDto dto) {

        Long userId = authService.getCurrentUserId();

        User author = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundWithIdException(userId));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundWithIdException(articleId));

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setAuthor(author);
        comment.setArticle(article);

        commentRepository.save(comment);

        return new MessageResponse("Comment added successfully!");
    }

}
