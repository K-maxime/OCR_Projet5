package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.UserNotFoundWithIdException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User getProfile(){
        //TODO update with jwt token
        return userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundWithIdException(1L));
    }

    public MessageResponse updateProfile(String email, String username, String password){

        //TODO update with jwt token
        User user = getProfile();

        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        userRepository.save(user);

        return new MessageResponse("User updated successfully!");

    }




}
