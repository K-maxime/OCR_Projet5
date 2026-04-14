package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ===== RELATIONS =====

    // Many Subjects → Many Articles
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();

    // Many Subjects ← Many Users (abonnements - côté inverse)
    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    private List<User> subscribers = new ArrayList<>();
}
