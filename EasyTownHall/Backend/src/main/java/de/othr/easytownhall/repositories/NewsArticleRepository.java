package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.newsArticle.NewsArticle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {

    @Query("SELECT n FROM NewsArticle n ORDER BY n.createdAt ASC")
    List<NewsArticle> findTopNArticles(Pageable pageable);

    @Query("SELECT n FROM NewsArticle n")
    List<NewsArticle> findAllOrdered(Sort sort);
}
