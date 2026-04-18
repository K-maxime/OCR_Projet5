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
        User oldUser = getProfile();

        User nexUser = new User();
        nexUser.setEmail(email);
        nexUser.setUsername(username);
        nexUser.setPassword(password);

        userRepository.delete(oldUser);
        userRepository.save(nexUser);

        return new MessageResponse("User updated successfully!");

    }




}
