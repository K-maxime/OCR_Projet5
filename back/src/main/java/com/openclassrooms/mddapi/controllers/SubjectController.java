package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.responses.SubjectResponseDto;
import com.openclassrooms.mddapi.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * Recupere la liste des themes.
     *
     * @return la liste des themes
     */
    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> getSubjects() {
        List<SubjectResponseDto> subjects = subjectService.getSubjects();

        return ResponseEntity.ok().body(subjects);
    }
}
