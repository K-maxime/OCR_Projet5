package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.SubjectNotFoundWithIdException;
import com.openclassrooms.mddapi.exceptions.SubscriptionAlreadyExistsException;
import com.openclassrooms.mddapi.exceptions.SubscriptionNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithIdException;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    void testIsSubscribed_WhenSubscriptionExists_ThenReturnTrue() {
        given(subscriptionRepository.existsByUserIdAndSubjectId(1L, 2L)).willReturn(true);

        boolean result = subscriptionService.isSubscribed(1L, 2L);

        assertTrue(result);
        verify(subscriptionRepository).existsByUserIdAndSubjectId(1L, 2L);
    }

    @Test
    void testIsSubscribed_WhenSubscriptionDoesNotExist_ThenReturnFalse() {
        given(subscriptionRepository.existsByUserIdAndSubjectId(1L, 2L)).willReturn(false);

        boolean result = subscriptionService.isSubscribed(1L, 2L);

        assertFalse(result);
        verify(subscriptionRepository).existsByUserIdAndSubjectId(1L, 2L);
    }

    @Test
    void testIsSubscribed_WhenIdsAreNull_ThenReturnFalse() {
        given(subscriptionRepository.existsByUserIdAndSubjectId(null, null)).willReturn(false);

        boolean result = subscriptionService.isSubscribed(null, null);

        assertFalse(result);
        verify(subscriptionRepository).existsByUserIdAndSubjectId(null, null);
    }

    @Test
    void testSuscribeToSubject_WhenSubscriptionIsValid_ThenSaveSubscriptionAndReturnMessage() {
        Subject subject = buildSubject(2L);
        User user = buildUser(1L);
        given(subjectRepository.findById(2L)).willReturn(Optional.of(subject));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        doReturn(false).when(subscriptionService).isSubscribed(1L, 2L);

        MessageResponse response = subscriptionService.suscribeToSubject(2L);

        ArgumentCaptor<Subscription> captor = ArgumentCaptor.forClass(Subscription.class);
        verify(subjectRepository).findById(2L);
        verify(userRepository).findById(1L);
        verify(subscriptionService).isSubscribed(1L, 2L);
        verify(subscriptionRepository).save(captor.capture());

        Subscription savedSubscription = captor.getValue();
        assertEquals(user, savedSubscription.getUser());
        assertEquals(subject, savedSubscription.getSubject());
        assertEquals("subscription avec succes ", response.getMessage());
    }

    @Test
    void testSuscribeToSubject_WhenSubjectDoesNotExist_ThenThrowException() {
        given(subjectRepository.findById(2L)).willReturn(Optional.empty());

        assertThrows(SubjectNotFoundWithIdException.class, () -> subscriptionService.suscribeToSubject(2L));

        verify(subjectRepository).findById(2L);
        verify(userRepository, never()).findById(any());
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void testSuscribeToSubject_WhenUserDoesNotExist_ThenThrowException() {
        Subject subject = buildSubject(2L);
        given(subjectRepository.findById(2L)).willReturn(Optional.of(subject));
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(UserNotFoundWithIdException.class, () -> subscriptionService.suscribeToSubject(2L));

        verify(subjectRepository).findById(2L);
        verify(userRepository).findById(1L);
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void testSuscribeToSubject_WhenSubscriptionAlreadyExists_ThenThrowException() {
        Subject subject = buildSubject(2L);
        User user = buildUser(1L);
        given(subjectRepository.findById(2L)).willReturn(Optional.of(subject));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        doReturn(true).when(subscriptionService).isSubscribed(1L, 2L);

        assertThrows(SubscriptionAlreadyExistsException.class, () -> subscriptionService.suscribeToSubject(2L));

        verify(subjectRepository).findById(2L);
        verify(userRepository).findById(1L);
        verify(subscriptionService).isSubscribed(1L, 2L);
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void testUnsuscribeToSubject_WhenSubscriptionExists_ThenDeleteSubscriptionAndReturnMessage() {
        Subject subject = buildSubject(2L);
        User user = buildUser(1L);
        Subscription subscription = new Subscription();
        subscription.setId(100L);
        subscription.setUser(user);
        subscription.setSubject(subject);

        given(subjectRepository.findById(2L)).willReturn(Optional.of(subject));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(subscriptionRepository.findByUserIdAndSubjectId(1L, 2L)).willReturn(Optional.of(subscription));

        MessageResponse response = subscriptionService.unsuscribeToSubject(2L);

        assertEquals("désabonnement avec succes ", response.getMessage());
        verify(subjectRepository).findById(2L);
        verify(userRepository).findById(1L);
        verify(subscriptionRepository).findByUserIdAndSubjectId(1L, 2L);
        verify(subscriptionRepository).delete(subscription);
    }

    @Test
    void testUnsuscribeToSubject_WhenSubjectDoesNotExist_ThenThrowException() {
        given(subjectRepository.findById(2L)).willReturn(Optional.empty());

        assertThrows(SubjectNotFoundWithIdException.class, () -> subscriptionService.unsuscribeToSubject(2L));

        verify(subjectRepository).findById(2L);
        verify(userRepository, never()).findById(any());
        verify(subscriptionRepository, never()).delete(any(Subscription.class));
    }

    @Test
    void testUnsuscribeToSubject_WhenUserDoesNotExist_ThenThrowException() {
        Subject subject = buildSubject(2L);
        given(subjectRepository.findById(2L)).willReturn(Optional.of(subject));
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(UserNotFoundWithIdException.class, () -> subscriptionService.unsuscribeToSubject(2L));

        verify(subjectRepository).findById(2L);
        verify(userRepository).findById(1L);
        verify(subscriptionRepository, never()).findByUserIdAndSubjectId(any(), any());
    }

    @Test
    void testUnsuscribeToSubject_WhenSubscriptionDoesNotExist_ThenThrowException() {
        Subject subject = buildSubject(2L);
        User user = buildUser(1L);
        given(subjectRepository.findById(2L)).willReturn(Optional.of(subject));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(subscriptionRepository.findByUserIdAndSubjectId(1L, 2L)).willReturn(Optional.empty());

        assertThrows(SubscriptionNotFoundException.class, () -> subscriptionService.unsuscribeToSubject(2L));

        verify(subjectRepository).findById(2L);
        verify(userRepository).findById(1L);
        verify(subscriptionRepository).findByUserIdAndSubjectId(1L, 2L);
        verify(subscriptionRepository, never()).delete(any(Subscription.class));
    }

    private User buildUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setEmail("user@mail.com");
        user.setUsername("user");
        user.setPassword("Password1!");
        return user;
    }

    private Subject buildSubject(Long id) {
        Subject subject = new Subject();
        subject.setId(id);
        subject.setName("Java");
        subject.setDescription("description");
        return subject;
    }
}
