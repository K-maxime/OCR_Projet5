package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithIdException;
import com.openclassrooms.mddapi.models.User;
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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private UserService userService;

    @Test
    void testGetProfile_WhenUserExists_ThenReturnUser() {
        User user = buildUser(1L, "john", "john@mail.com", "Password1!");
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        User result = userService.getProfile();

        assertSame(user, result);
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetProfile_WhenUserDoesNotExist_ThenThrowException() {
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(UserNotFoundWithIdException.class, () -> userService.getProfile());

        verify(userRepository).findById(1L);
    }

    @Test
    void testGetProfile_WhenRepositoryReturnsEmptyAgain_ThenThrowException() {
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(UserNotFoundWithIdException.class, () -> userService.getProfile());

        verify(userRepository).findById(1L);
    }

    @Test
    void testUpdateProfile_WhenUserExists_ThenUpdateAndReturnMessage() {
        User existingUser = buildUser(1L, "john", "john@mail.com", "Password1!");
        doReturn(existingUser).when(userService).getProfile();

        MessageResponse response = userService.updateProfile("new@mail.com", "newjohn", "NewPassword1!");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).getProfile();
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("new@mail.com", savedUser.getEmail());
        assertEquals("newjohn", savedUser.getUsername());
        assertEquals("NewPassword1!", savedUser.getPassword());
        assertEquals("User updated successfully!", response.getMessage());
    }

    @Test
    void testUpdateProfile_WhenProfileDoesNotExist_ThenThrowException() {
        doThrow(new UserNotFoundWithIdException(1L)).when(userService).getProfile();

        assertThrows(UserNotFoundWithIdException.class,
                () -> userService.updateProfile("new@mail.com", "newjohn", "NewPassword1!"));

        verify(userService).getProfile();
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateProfile_WhenRepositorySaveFails_ThenPropagateException() {
        User existingUser = buildUser(1L, "john", "john@mail.com", "Password1!");
        doReturn(existingUser).when(userService).getProfile();
        RuntimeException databaseException = new RuntimeException("database error");
        org.mockito.Mockito.doThrow(databaseException).when(userRepository).save(any(User.class));

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> userService.updateProfile("new@mail.com", "newjohn", "NewPassword1!"));

        assertEquals("database error", thrown.getMessage());
        verify(userService).getProfile();
        verify(userRepository).save(any(User.class));
    }

    private User buildUser(Long id, String username, String email, String password) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
