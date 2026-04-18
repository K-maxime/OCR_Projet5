package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.request.CreateCommentRequestDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.ArticleNotFoundWithIdException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleService articleService;
    private final UserService userService;


    /**
     * Crée un nouveau commentaire pour un article donné.
     *
     * @param articleId L'ID de l'article à commenter
     * @param dto   Le contenu du commentaire
     * @return MessageResponse indiquant le succès de l'opération
     */
    public MessageResponse createComment(Long articleId, CreateCommentRequestDto dto) {

        User author = userService.getProfile();;

        Article article = articleService.getArticleById(articleId);

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setAuthor(author);
        comment.setArticle(article);

        commentRepository.save(comment);

        return new MessageResponse("Comment added successfully!");
    }

}
