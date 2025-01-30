package de.othr.easytownhall.models.entities.newsArticle;


import de.othr.easytownhall.models.entities.TestUserModel;
import de.othr.easytownhall.models.enums.ArticleCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name="NewsArticles")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @Lob // Für größere Texte, falls nötig
    private String content;

    @NonNull
    private String author;

    @Enumerated(EnumType.STRING)
    private ArticleCategory category;

    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @NonNull
    private boolean isPublished = false;

    public static NewsArticle createNewsArticle(String title, String content, String author, ArticleCategory category) {
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setTitle(title);
        newsArticle.setContent(content);
        newsArticle.setAuthor(author);
        newsArticle.setCategory(category);
        newsArticle.setPublished(true);
        Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        newsArticle.setCreatedAt(date);
        return newsArticle;
    }
}
