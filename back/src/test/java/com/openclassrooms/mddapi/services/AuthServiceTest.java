package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.UserAlreadyExistsException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithLoginOrInvalidPasswordException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void testLogin_WhenCredentialsAreValid_ThenReturnUser() {
        User user = buildUser(1L, "john", "john@mail.com", "Password1!");
        String excptedToken = jwtTokenProvider.generateToken(user.getUsername());
        given(userRepository.findByEmailOrUsername("john", "john")).willReturn(Optional.of(user));

        String result = authService.login("john", "Password1!");

        assertSame(excptedToken, result);
        verify(userRepository).findByEmailOrUsername("john", "john");
    }

    @Test
    void testLogin_WhenUserDoesNotExist_ThenThrowException() {
        given(userRepository.findByEmailOrUsername("unknown", "unknown")).willReturn(Optional.empty());

        assertThrows(UserNotFoundWithLoginOrInvalidPasswordException.class,
                () -> authService.login("unknown", "Password1!"));

        verify(userRepository).findByEmailOrUsername("unknown", "unknown");
    }

    @Test
    void testLogin_WhenPasswordIsInvalid_ThenThrowException() {
        User user = buildUser(1L, "john", "john@mail.com", "Password1!");
        given(userRepository.findByEmailOrUsername("john", "john")).willReturn(Optional.of(user));

        assertThrows(UserNotFoundWithLoginOrInvalidPasswordException.class,
                () -> authService.login("john", "WrongPassword1!"));

        verify(userRepository).findByEmailOrUsername("john", "john");
    }

    @Test
    void testRegister_WhenUserDoesNotExist_ThenSaveUser() {
        given(userRepository.findByEmailOrUsername("john@mail.com", "john")).willReturn(Optional.empty());

        authService.register("john", "john@mail.com", "Password1!");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).findByEmailOrUsername("john@mail.com", "john");
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("john", savedUser.getUsername());
        assertEquals("john@mail.com", savedUser.getEmail());
        assertEquals("Password1!", savedUser.getPassword());
    }

    @Test
    void testRegister_WhenEmailAlreadyExists_ThenThrowException() {
        User existingUser = buildUser(1L, "john", "john@mail.com", "Password1!");
        given(userRepository.findByEmailOrUsername("john@mail.com", "newuser")).willReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class,
                () -> authService.register("newuser", "john@mail.com", "Password1!"));

        verify(userRepository).findByEmailOrUsername("john@mail.com", "newuser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_WhenUsernameAlreadyExists_ThenThrowException() {
        User existingUser = buildUser(1L, "john", "other@mail.com", "Password1!");
        given(userRepository.findByEmailOrUsername("new@mail.com", "john")).willReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class,
                () -> authService.register("john", "new@mail.com", "Password1!"));

        verify(userRepository).findByEmailOrUsername("new@mail.com", "john");
        verify(userRepository, never()).save(any(User.class));
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
