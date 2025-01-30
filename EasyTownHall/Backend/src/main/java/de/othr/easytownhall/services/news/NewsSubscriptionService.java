package de.othr.easytownhall.services.news;

import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.citizen.Citizen;
import de.othr.easytownhall.models.entities.newsArticle.NewsSubscription;
import de.othr.easytownhall.repositories.CitizenRepository;
import de.othr.easytownhall.repositories.NewsSubscriptionRepository;
import de.othr.easytownhall.repositories.UsersRepository;
import de.othr.easytownhall.services.CitizenService;
import de.othr.easytownhall.services.EmailService;
import de.othr.easytownhall.services.MyUserService;
import de.othr.easytownhall.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class NewsSubscriptionService {

    private NewsSubscriptionRepository newsSubscriptionRepository;
    private UsersRepository usersRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CitizenService citizenService;
    @Autowired
    private TemplateService templateService;

    public NewsSubscriptionService(NewsSubscriptionRepository newsSubscriptionRepository, UsersRepository usersRepository) {
        this.newsSubscriptionRepository = newsSubscriptionRepository;
        this.usersRepository = usersRepository;
    }

    public NewsSubscription addSubscription(Long userid) {
        Optional<User> userOpt = usersRepository.findById(userid);
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            NewsSubscription newsSubscription = new NewsSubscription();
            newsSubscription.setUser(user);
            return newsSubscriptionRepository.save(newsSubscription);
        }
        return null;
    }

    public boolean isSubscribed(Long userId) {
        return newsSubscriptionRepository.existsByUserId(userId);
    }

    public void unsubscribe(Long userId) {
        if (isSubscribed(userId)) {
            newsSubscriptionRepository.deleteByUserId(userId);
        }
    }

    public void confirmationEmail(Long userId, String bodyText) throws IOException {
        Optional<User> userOpt = usersRepository.findById(userId);
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            Citizen citizen = citizenService.getCitizenByUser(user);

            String template = templateService.loadTemplateSubscription("emails/email_template.html");
            String header = "Newsletter Abonnement";
            String userName = "Bürger";
            if(citizen.getFirstName() != null && citizen.getLastName() != null) {
                userName = citizen.getFirstName() + " " + citizen.getLastName();
            }
            String content = String.format(
                    "<p>Hallo %s,</p>\n" +
                            "<p>Sie haben erfolgreich den Newsletter <b>%s</b></p>\n" +
                            "<p>Mit freundlichen Grüßen,<br>Ihr Rathaus Regensburg</p>",
                    userName, bodyText);

            String body = template
                    .replace("{{header}}", header)
                    .replace("{{body}}", content);

            // E-Mail versenden
            emailService.sendEmail(user.getEmail(), header, body);
        }
    }

}
