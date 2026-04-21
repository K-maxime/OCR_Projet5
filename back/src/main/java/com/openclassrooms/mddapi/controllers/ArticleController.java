package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.request.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.request.CreateCommentRequestDto;
import com.openclassrooms.mddapi.dto.responses.ArticleResponseDto;
import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.ArticleNotFoundWithIdException;
import com.openclassrooms.mddapi.exceptions.SubjectNotFoundWithIdException;
import com.openclassrooms.mddapi.exceptions.UnknowSortException;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
 * Contrôleur REST pour la gestion des articles.
 *
 * Fournit les endpoints pour :
 * - La liste des articles
 * - L'article demandé par son Id
 * - La création d'un article
 * - La création d'un commentaire
 *
 *  Tous les endpoints nécessitent une authentification JWT.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
@Tag(name = "Article endpoints", description = "Endpoints for managing articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final ArticleMapper articleMapper;

    /**
     * Récupère la liste des articles avec option de tri.
     *
     * @param sort paramètre optionnel contenant le type de tri ascendant ou descendant
     * @return ResponseEntity contenant la liste des articles
     * @throws UnknowSortException si le type de tri n'est pas reconnu
     */
    @Operation(summary = "Get all articles with sorting",
            description = "Retrieve all articles with optional sorting by creation date (asc or desc)")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Articles retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid sort parameter - must be empty, 'asc' or 'desc'"),
            @ApiResponse(responseCode = "404", description = "User not found"),  // TODO Temporaire avant JWT
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> getArticles(@RequestParam(required = false) String sort) {
        return ResponseEntity.ok().body(this.articleMapper.toDto(articleService.getAllArticles(sort)));
    }

    /**
     * Récupère la liste l'article demandé par son Id.
     *
     * @param id identifiant de l'article à récupérer
     * @return ResponseEntity contenant l'a liste des 'articles
     * @throws ArticleNotFoundWithIdException si l'article n'est pas trouvé
     */
    @Operation(summary = "Get the article with the given id",
            description = "Retrieve the article with the given id")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.articleMapper.toDto(articleService.getArticleById(id)));
    }

    /**
     * Crée et enregistre un nouvel article.
     *
     * @param request DTO contenant (subjectId ,title ,content)
     * @return ResponseEntity contenant un message de confirmation
     * @throws SubjectNotFoundWithIdException si le thème de l'article n'est pas trouvé
     */
    @Operation(summary = "Create a new article",
            description = "Create and publish a new article")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateArticleRequestDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),  // TODO Temporaire avant JWT
            @ApiResponse(responseCode = "404", description = "Subject not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (missing/empty fields or invalid lengths)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<MessageResponse> createArticle(@Valid @RequestBody CreateArticleRequestDto request) {
        return ResponseEntity.ok(this.articleService.createArticle(request));
    }

    /**
     * Crée et enregistre un nouvel commentaire à l'article demandé.
     *
     * @param request DTO contenant (content)
     * @return ResponseEntity contenant un message de confirmation
     * @throws ArticleNotFoundWithIdException si l'article n'est pas trouvé
     */
    @Operation(summary = "Create a new comment",
            description = "Create and publish a new comment")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateCommentRequestDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),  // TODO Temporaire avant JWT
            @ApiResponse(responseCode = "404", description = "Article not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (missing/empty fields or invalid lengths)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/{id}/comments")
    public ResponseEntity<MessageResponse> createArticleComment(@PathVariable Long id, @Valid @RequestBody CreateCommentRequestDto request) {
        return ResponseEntity.ok(this.commentService.createComment(id, request));
    }
}
