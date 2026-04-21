package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.SubjectResponseDto;
import com.openclassrooms.mddapi.exceptions.UnknowSortException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithIdException;
import com.openclassrooms.mddapi.mapper.SubjectMapper;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service métier pour la gestion des thèmes.
 *
 * Gère :
 * - La recherche des articles
 */
@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final SubjectMapper subjectMapper;
    private final SubscriptionRepository subscriptionRepository;


    /**
     * Retourne la liste des thèmes en indiquant l'abonnement ou non de l'utilisateur.
     *
     * @return une liste l'objet thèmes
     */
    public List<SubjectResponseDto> getSubjects() {

         //TODO update with jwt token
        Long userid = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundWithIdException(1L))
                .getId();

        List<SubjectResponseDto> subjects = subjectRepository.findAll().stream()
                .map(subject -> {
                    SubjectResponseDto dto = subjectMapper.toDto(subject);
                    dto.setSubscribed( subscriptionRepository.existsByUserIdAndSubjectId(userid, subject.getId()));
                    return dto;
                }).toList();

        return subjects;
    }

}
