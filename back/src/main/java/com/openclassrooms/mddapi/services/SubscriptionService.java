package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.SubjectNotFoundWithIdException;
import com.openclassrooms.mddapi.exceptions.SubscriptionAlreadyExistsException;
import com.openclassrooms.mddapi.exceptions.SubscriptionNotFoundException;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubjectService subjectService;
    private final UserService userService;

    /**
     * Vérifie si un utilisateur est abonné à un sujet
     */
    public boolean isSubscribed(Long userId, Long subjectId) {
        return subscriptionRepository.existsByUserIdAndSubjectId(userId, subjectId);
    }

    public MessageResponse suscribeToSubject (Long subjectId){

        Subject subject = subjectService.getSubjectById(subjectId);
        User user = userService.getProfile();;

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

        Subject subject = subjectService.getSubjectById(subjectId);
        User user = userService.getProfile();;

        Subscription subscription = subscriptionRepository.findByUserIdAndSubjectId(user.getId(), subjectId)
                .orElseThrow(() -> new SubscriptionNotFoundException(subjectId));

        subscriptionRepository.delete(subscription);
        return new MessageResponse("désabonnement avec succes ");



    }
}
