package de.othr.easytownhall.controller;

import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.newsArticle.NewsArticle;
import de.othr.easytownhall.models.entities.newsArticle.NewsSubscription;
import de.othr.easytownhall.services.EmailService;
import de.othr.easytownhall.services.MyUserService;
import de.othr.easytownhall.services.news.NewsArticleService;
import de.othr.easytownhall.services.news.NewsSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsArticleController {
    @Autowired
    private NewsArticleService newsArticleService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private NewsSubscriptionService newsSubscriptionService;

    @GetMapping("getArticle/{id}")
    public ResponseEntity<NewsArticle> getArticle(@PathVariable long id) {
        NewsArticle article = this.newsArticleService.getArticleById(id);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<NewsArticle>> getAllArticles() {
        List<NewsArticle> articles = newsArticleService.getAll();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/getAllOrdered")
    public ResponseEntity<List<NewsArticle>> getAllOrdered(
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "asc") String direction) {
        List<NewsArticle> articles = newsArticleService.getAllArticlesOrdered(orderBy, direction);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/getRecent")
    public ResponseEntity<List<NewsArticle>> getRecentArticles() {
        List<NewsArticle> articles = newsArticleService.getArticles(3);
        if (articles == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articles);
    }

    @PostMapping("/addArticle")
    public ResponseEntity<NewsArticle> addArticle(@RequestBody NewsArticle article) {
        NewsArticle savedArticle = newsArticleService.addArticle(article);
        return ResponseEntity.ok(savedArticle);
    }

    @DeleteMapping("/deleteArticle/{id}")
    public ResponseEntity<Boolean> deleteArticle(@PathVariable Long id) {
        boolean isDeleted = newsArticleService.deleteArticle(id);
        if (isDeleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/updateArticle/{id}")
    public ResponseEntity<NewsArticle> updateArticle(@PathVariable Long id, @RequestBody NewsArticle article) {
        NewsArticle savedArticle = newsArticleService.updateArticle(id, article);
        return ResponseEntity.ok(savedArticle);
    }

    @GetMapping("/subscribe/{userid}")
    public ResponseEntity<Boolean> subscribe(@PathVariable Long userid) {
        NewsSubscription sub = this.newsSubscriptionService.addSubscription(userid);
        if(sub != null) {
            try {
                newsSubscriptionService.confirmationEmail(userid, "abonniert");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(500).build();
    }

    @GetMapping("/isSubbed/{userid}")
    public ResponseEntity<Boolean> isSubcribed(@PathVariable Long userid) {
        boolean isSubbed = newsSubscriptionService.isSubscribed(userid);
        return ResponseEntity.ok(isSubbed);
    }

    @GetMapping("/unsubscribe/{userid}")
    public ResponseEntity<String> unsubscribe(@PathVariable Long userid) {
        newsSubscriptionService.unsubscribe(userid);
        try {
            newsSubscriptionService.confirmationEmail(userid, "deabonniert");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sendMails")
    public void sendMails() {
        newsArticleService.sendNewsLetter();
    }
}
