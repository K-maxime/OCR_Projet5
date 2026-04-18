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

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    /**
     * Vérifie si un utilisateur est abonné à un sujet
     */
    public boolean isSubscribed(Long userId, Long subjectId) {
        return subscriptionRepository.existsByUserIdAndSubjectId(userId, subjectId);
    }

    public MessageResponse suscribeToSubject (Long subjectId){

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundWithIdException(subjectId));
        //TODO update with jwt token
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundWithIdException(1L));

        if (isSubscribed(user.getId(), subject.getId())){// vérifie si la subscription existe deja
            throw new SubscriptionAlreadyExistsException();
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubject(subject);
        subscriptionRepository.save(subscription);
        return new MessageResponse("subscription avec succes ");
    }

    public MessageResponse unsuscribeToSubject (Long subjectId) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundWithIdException(subjectId));
        //TODO update with jwt token
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundWithIdException(1L));

        Subscription subscription = subscriptionRepository.findByUserIdAndSubjectId(user.getId(), subjectId)
                .orElseThrow(() -> new SubscriptionNotFoundException(subjectId));

        subscriptionRepository.delete(subscription);
        return new MessageResponse("désabonnement avec succes ");

    }
}
