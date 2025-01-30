package de.othr.easytownhall.config;

import de.othr.easytownhall.models.enums.ArticleCategory;
import de.othr.easytownhall.models.entities.newsArticle.NewsArticle;
import de.othr.easytownhall.models.entities.TestUserModel;
import de.othr.easytownhall.repositories.NewsArticleRepository;
import de.othr.easytownhall.repositories.TestUserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;


@Configuration
public class DatabaseInit {

    @Autowired
    private TestUserRepository testUserRepository;

    @Autowired
    private NewsArticleRepository newsArticleRepository;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInit.class);
    final String articleContent =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore" +
            " et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. " +
            "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, " +
            "consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, " +
            "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, " +
            "no sea takimata sanctus est Lorem ipsum dolor sit amet.";

    // Erstellt jedes Mal, wenn der "server" gestartet wird diese TestObjekte, da in den application.properties =>
    // spring.jpa.hibernate.ddl-auto=create-drop
    @Bean
    @Transactional
    CommandLineRunner initDatabase() {
        return args -> {
            // Daten in die Datenbank einfügen
            if (testUserRepository.count() == 0) { // Nur, wenn die Tabelle leer ist
                testUserRepository.save(new TestUserModel("John Doe", 30));
                testUserRepository.save(new TestUserModel("Jane Doe", 25));
                testUserRepository.save(new TestUserModel("Chris Doe", 23));
                logger.info("Datenbank wurde initialisiert.");
            }

            if(newsArticleRepository.count() == 0) {
                newsArticleRepository.save(NewsArticle.createNewsArticle(
                        "Kultur Artikel",
                        articleContent,
                        "John Doe",
                        ArticleCategory.EDUCATION_AND_CULTURE

                ));
                newsArticleRepository.save(NewsArticle.createNewsArticle(
                        "Sport Artikel",
                        articleContent,
                        "David Smith",
                        ArticleCategory.LEISURE_AND_SPORTS
                ));
                newsArticleRepository.save(NewsArticle.createNewsArticle(
                        "Ankündigung",
                        articleContent,
                        "Spongebob",
                        ArticleCategory.GENERAL_ANNOUNCEMENTS
                ));
            }
            logger.info("Datenbank ist initialisiert mit Demodaten.");
        };
    }
}
