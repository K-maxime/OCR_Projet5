package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ArticleRepository extends JpaRepository<Article, Long> {

    @Query("""
        SELECT a FROM Article a 
        WHERE a.subject IN (
            SELECT s FROM Subscription sub 
            JOIN sub.subject s 
            WHERE sub.user.id = :userId
        )
        ORDER BY a.createdAt DESC
        """)
    List<Article> findArticlesByUserSubscriptionsDesc(@Param("userId") Long userId);

    @Query("""
        SELECT a FROM Article a 
        WHERE a.subject IN (
            SELECT s FROM Subscription sub 
            JOIN sub.subject s 
            WHERE sub.user.id = :userId
        )
        ORDER BY a.createdAt ASC
        """)
    List<Article> findArticlesByUserSubscriptionsAsc(@Param("userId") Long userId);
}
