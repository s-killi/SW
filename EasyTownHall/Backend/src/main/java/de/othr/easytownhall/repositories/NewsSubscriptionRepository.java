package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.newsArticle.NewsSubscription;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsSubscriptionRepository extends  JpaRepository<NewsSubscription, Long>{

    @Query("SELECT CASE WHEN COUNT(ns) > 0 THEN TRUE ELSE FALSE END " +
            "FROM NewsSubscription ns WHERE ns.user.id = :userId")
    boolean existsByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM NewsSubscription ns WHERE ns.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    @Query("SELECT ns.user FROM NewsSubscription ns")
    List<User> findAllSubscribedUsers();
}
