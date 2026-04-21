package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.responses.SubjectResponseDto;
import com.openclassrooms.mddapi.exceptions.UnknowSortException;
import com.openclassrooms.mddapi.services.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des thèmes.
 *
 * Fournit les endpoints pour :
 * - La liste des thèmes
 *
 *  Tous les endpoints nécessitent une authentification JWT.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
@Tag(name = "Subject endpoints", description = "Endpoints for managing subjects")
public class SubjectController {

    private final SubjectService subjectService;

    /**
     * Récupère la liste des articles avec option de tri.
     *
     * @return ResponseEntity contenant la liste des thèmes
     */
    @Operation(summary = "Get all subjects", description = "return all subjects")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subjects retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),  // TODO Temporaire avant JWT
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> getSubjects() {
        List<SubjectResponseDto> subjects = subjectService.getSubjects();

        return ResponseEntity.ok().body(subjects);
    }
}
