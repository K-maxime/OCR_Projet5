package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.SubjectResponseDto;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithIdException;
import com.openclassrooms.mddapi.mapper.SubjectMapper;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubjectMapper subjectMapper;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubjectService subjectService;

    @Test
    void testGetSubjects_WhenUserExistsAndSubscriptionsMixed_ThenReturnMappedSubjects() {
        User user = buildUser(1L);
        Subject java = buildSubject(10L, "Java");
        Subject spring = buildSubject(11L, "Spring");
        SubjectResponseDto javaDto = new SubjectResponseDto(10L, "Java", "Java desc", false);
        SubjectResponseDto springDto = new SubjectResponseDto(11L, "Spring", "Spring desc", false);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(subjectRepository.findAll()).willReturn(List.of(java, spring));
        given(subjectMapper.toDto(java)).willReturn(javaDto);
        given(subjectMapper.toDto(spring)).willReturn(springDto);
        given(subscriptionRepository.existsByUserIdAndSubjectId(1L, 10L)).willReturn(true);
        given(subscriptionRepository.existsByUserIdAndSubjectId(1L, 11L)).willReturn(false);

        List<SubjectResponseDto> result = subjectService.getSubjects();

        assertEquals(2, result.size());
        assertTrue(result.get(0).isSubscribed());
        assertFalse(result.get(1).isSubscribed());
        verify(userRepository).findById(1L);
        verify(subjectRepository).findAll();
        verify(subjectMapper).toDto(java);
        verify(subjectMapper).toDto(spring);
    }

    @Test
    void testGetSubjects_WhenUserDoesNotExist_ThenThrowException() {
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(UserNotFoundWithIdException.class, () -> subjectService.getSubjects());

        verify(userRepository).findById(1L);
        verify(subjectRepository, never()).findAll();
    }

    @Test
    void testGetSubjects_WhenNoSubjectsExist_ThenReturnEmptyList() {
        User user = buildUser(1L);
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(subjectRepository.findAll()).willReturn(List.of());

        List<SubjectResponseDto> result = subjectService.getSubjects();

        assertTrue(result.isEmpty());
        verify(userRepository).findById(1L);
        verify(subjectRepository).findAll();
    }

    private User buildUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setEmail("user@mail.com");
        user.setUsername("user");
        user.setPassword("Password1!");
        return user;
    }

    private Subject buildSubject(Long id, String name) {
        Subject subject = new Subject();
        subject.setId(id);
        subject.setName(name);
        subject.setDescription(name + " desc");
        return subject;
    }
}
