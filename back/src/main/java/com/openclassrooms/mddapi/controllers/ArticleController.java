package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.request.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.request.CreateCommentRequestDto;
import com.openclassrooms.mddapi.dto.responses.ArticleResponseDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controleur des endpoints d'articles.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final ArticleMapper articleMapper;

    /**
     * Recupere le fil d'actualite des articles.
     *
     * @param sort le sens de tri optionnel
     * @return la liste des articles
     */
    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> getArticles(@RequestParam(required = false) String sort) {
        return ResponseEntity.ok().body(this.articleMapper.toDto(articleService.getAllArticles(sort)));
    }

    /**
     * Recupere le detail d'un article.
     *
     * @param id l'identifiant de l'article
     * @return le detail de l'article
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.articleMapper.toDto(articleService.getArticleById(id)));
    }

    /**
     * Cree un nouvel article.
     *
     * @param request les donnees de creation
     * @return l'article cree
     */
    @PostMapping
    public ResponseEntity<MessageResponse> createArticle(@RequestBody CreateArticleRequestDto request) {
        return ResponseEntity.ok(this.articleService.createArticle(request));
    }

    /**
     * Ajoute un commentaire a un article.
     *
     * @param id l'identifiant de l'article
     * @param request le contenu du commentaire
     * @return le commentaire cree
     */
    @PostMapping("/{id}/comments")
    public ResponseEntity<MessageResponse> createArticleComment(@PathVariable Long id, @RequestBody CreateCommentRequestDto request) {
        return ResponseEntity.ok(this.commentService.createComment(id, request));
    }
}
