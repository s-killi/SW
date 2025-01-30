package de.othr.easytownhall.services.news;

import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.newsArticle.NewsArticle;
import de.othr.easytownhall.repositories.NewsArticleRepository;
import de.othr.easytownhall.repositories.NewsSubscriptionRepository;
import de.othr.easytownhall.services.EmailService;
import de.othr.easytownhall.services.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class NewsArticleService {
    @Autowired
    private NewsArticleRepository newsArticleRepository;
    @Autowired
    private NewsSubscriptionRepository newsSubscriptionRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TemplateService templateService;

    private static final Logger logger = LoggerFactory.getLogger(NewsArticleService.class);

    public List<NewsArticle> getAll() {
        return newsArticleRepository.findAll();
    }

    public NewsArticle addArticle(NewsArticle newsArticle) {
        logger.info("Adding article: " + newsArticle.getTitle());
        newsArticle.setUpdatedAt(new Date());
        return newsArticleRepository.save(newsArticle);
    }

    public NewsArticle updateArticle(Long id, NewsArticle updatedArticle) {
        Optional<NewsArticle> article = newsArticleRepository.findById(id);
        if (article.isPresent()) {
            NewsArticle existingArticle = article.get();

            existingArticle.setTitle(updatedArticle.getTitle());
            existingArticle.setContent(updatedArticle.getContent());
            existingArticle.setCategory(updatedArticle.getCategory());
            existingArticle.setPublished(updatedArticle.isPublished());
            existingArticle.setAuthor(updatedArticle.getAuthor());
            existingArticle.setUpdatedAt(new Date());
            return newsArticleRepository.save(existingArticle);
        }
        return null;
    }

    public boolean deleteArticle(Long id) {
        Optional<NewsArticle> article = newsArticleRepository.findById(id);
        if (article.isPresent()) {
           newsArticleRepository.deleteById(id);
           return true;
        }
        return false;
    }

    public NewsArticle getArticleById(long id) {
        Optional<NewsArticle> article = newsArticleRepository.findById(id);
        if (article.isPresent()) {
            return article.get();
        }
        return null;
    }

    public List<NewsArticle> getArticles(int n) {
        Pageable pageable = PageRequest.of(0, n); //
        return newsArticleRepository.findTopNArticles(pageable);
    }

    public List<NewsArticle> getAllArticlesOrdered(String orderBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
        return newsArticleRepository.findAllOrdered(sort);
    }

    private String convertArticleToHtml() {
       List<NewsArticle> articles = getArticles(3);

        StringBuilder htmlBuilder = new StringBuilder();

        for (NewsArticle article : articles) {
            htmlBuilder
                    .append("<h3>").append(article.getTitle()).append("</h3>")
                    .append("<p>Kategorie: ").append(article.getCategory().getDisplayName()).append("</p>")
                    .append("<p>").append(article.getContent() != null ? article.getContent() : "Kein Inhalt verfügbar").append("</p>")
                    .append("<p>Geschrieben von: ").append(article.getAuthor())
                    .append(" am ").append(formatDate(article.getCreatedAt())).append("</p>")
                    .append("<hr></hr>");
        }

        return htmlBuilder.toString();
    }

    private String formatDate(Date date) {
        if (date == null) {
            return "Unbekanntes Datum";
        }
        // Konvertiere das Datum in ein deutsches Format (dd. MMMM yyyy)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MMMM yyyy", Locale.GERMAN);
        return dateFormat.format(date);
    }

    public void sendNewsLetter(){
        List<User> users = newsSubscriptionRepository.findAllSubscribedUsers();

        String template = templateService.loadTemplateSubscription("emails/email_template.html");
        String header = "Aktuelle Nachrichten des Rathauses Regensburg";
        String body = convertArticleToHtml();

        String templateBody = template
                .replace("{{header}}", header)
                .replace("{{body}}", body);
        for(User user : users) {
            emailService.sendEmail(user.getEmail(), "EasyTownHall News", templateBody);
        }
    }
}
