package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    boolean existsByUserAndSubject(User user, Subject subject);

    Optional<Subscription> findByUserIdAndSubjectId(Long userId, Long subjectId);

}
