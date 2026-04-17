package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.dto.responses.SubjectResponseDto;
import com.openclassrooms.mddapi.mapper.SubjectMapper;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controleur des endpoints de themes.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    /**
     * Recupere la liste des themes.
     *
     * @return la liste des themes
     */
    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> getSubjects() {
        List<Subject> subjects = subjectService.getSubjects();

        return ResponseEntity.ok().body(this.subjectMapper.toDto(subjects));
    }

    /**
     * Abonne l'utilisateur a un theme.
     *
     * @param id l'identifiant du theme
     * @return une confirmation d'abonnement
     */
    @PostMapping("/{id}/subscribe")
    public ResponseEntity<MessageResponse> subscribeToSubject(@PathVariable Long id) {
        return ResponseEntity.ok(this.subjectService.suscribeToSubject(id));
    }

    /**
     * Desabonne l'utilisateur d'un theme.
     *
     * @param id l'identifiant du theme
     * @return une confirmation de desabonnement
     */
    @DeleteMapping("/{id}/unsubscribe")
    public ResponseEntity<MessageResponse> unsubscribeFromSubject(@PathVariable Long id) {
        return ResponseEntity.ok(this.subjectService.unsuscribeToSubject(id));
    }
}
