package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.SubjectResponseDto;
import com.openclassrooms.mddapi.exceptions.SubjectNotFoundWithIdException;
import com.openclassrooms.mddapi.mapper.SubjectMapper;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserService userService;
    private final SubjectMapper subjectMapper;
    private final SubscriptionService subscriptionService;


    public List<SubjectResponseDto> getSubjects() {

        Long userid = userService.getProfile().getId();
        List<SubjectResponseDto> subjects = subjectRepository.findAll().stream()
                .map(subject -> {
                    SubjectResponseDto dto = subjectMapper.toDto(subject);
                    dto.setSubscribed( subscriptionService.isSubscribed(userid, subject.getId()));
                    return dto;
                }).toList();

        return subjects;
    }

    public Subject getSubjectById(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundWithIdException(subjectId));
    }
}
