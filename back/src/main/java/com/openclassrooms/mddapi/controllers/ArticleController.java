package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.CreateCommentRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controleur des endpoints d'articles.
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    /**
     * Recupere le fil d'actualite des articles.
     *
     * @param sort le sens de tri optionnel
     * @return la liste des articles
     */
    @GetMapping
    public ResponseEntity<?> getArticles(@RequestParam(required = false) String sort) {
        return ResponseEntity.ok("Endpoint non implémente");
    }

    /**
     * Recupere le detail d'un article.
     *
     * @param id l'identifiant de l'article
     * @return le detail de l'article
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok("Endpoint non implémente");
    }

    /**
     * Cree un nouvel article.
     *
     * @param request les donnees de creation
     * @return l'article cree
     */
    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody CreateArticleRequestDto request) {
        return ResponseEntity.ok("Endpoint non implémente");
    }

    /**
     * Ajoute un commentaire a un article.
     *
     * @param id l'identifiant de l'article
     * @param request le contenu du commentaire
     * @return le commentaire cree
     */
    @PostMapping("/{id}/comments")
    public ResponseEntity<?> createArticleComment(@PathVariable Long id, @RequestBody CreateCommentRequestDto request) {
        return ResponseEntity.ok("Endpoint non implémente");
    }
}
