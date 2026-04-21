package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Service métier pour la gestion de l'abonnement.
 *
 * Gère :
 * - L'abonnement et le désabonnement à un thème
 */
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    /**
     * Indique si l'utilisateur est abonné à un thème donné.
     *
     * @param userId identifiant de l'utilisateur
     * @param subjectId identifiant du thème
     * @return boolean indiquant si l'utilisateur est abonné au thème
     */
    public boolean isSubscribed(Long userId, Long subjectId) {
        return subscriptionRepository.existsByUserIdAndSubjectId(userId, subjectId);
    }

    /**
     * Crée un nouvel abonnement entre un utilisateur et un thème.
     *
     * @param subjectId identifiant du thème
     * @return MessageResponse contenant le message de confirmation
     * @throws SubjectNotFoundWithIdException si le thème n'est pas trouvé
     * @throws SubscriptionAlreadyExistsException si l'abonnement existe deja
     */
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

    /**
     * Supprime l'abonnement entre un utilisateur et un thème.
     *
     * @param subjectId identifiant du thème
     * @return MessageResponse contenant le message de confirmation
     * @throws SubjectNotFoundWithIdException si le thème n'est pas trouvé
     * @throws SubscriptionNotFoundException si l'abonnement n'existe pas
     */
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
