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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;


    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    public MessageResponse suscribeToSubject (Long subjectId){

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundWithIdException(subjectId));
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundWithIdException(1L));

        if (subscriptionRepository.existsByUserAndSubject(user, subject)) {
            throw new SubscriptionAlreadyExistsException();
        }
        else {
            Subscription subscription = new Subscription();
            subscription.setUser(user);
            subscription.setSubject(subject);
            subscriptionRepository.save(subscription);
            return new MessageResponse("subscription avec succes ");
        }
    }

    public MessageResponse unsuscribeToSubject (Long subjectId) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundWithIdException(subjectId));
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundWithIdException(1L));


        Subscription subscription = subscriptionRepository.findByUserIdAndSubjectId(user.getId(), subjectId)
                .orElseThrow(() -> new SubscriptionNotFoundException(subjectId));
        subscriptionRepository.delete(subscription);
        return new MessageResponse("desabonnement avec succes ");
    }

}
