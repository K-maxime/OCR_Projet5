package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.responses.MessageResponse;
import com.openclassrooms.mddapi.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects/{id}/subscribe")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    /**
     * Abonne l'utilisateur a un theme.
     *
     * @param id l'identifiant du theme
     * @return une confirmation d'abonnement
     */
    @PostMapping()
    public ResponseEntity<MessageResponse> subscribeToSubject(@PathVariable Long id) {
        return ResponseEntity.ok(this.subscriptionService.suscribeToSubject(id));
    }

    /**
     * Desabonne l'utilisateur d'un theme.
     *
     * @param id l'identifiant du theme
     * @return une confirmation de desabonnement
     */
    @DeleteMapping()
    public ResponseEntity<MessageResponse> unsubscribeFromSubject(@PathVariable Long id) {
        return ResponseEntity.ok(this.subscriptionService.unsuscribeToSubject(id));
    }
}

