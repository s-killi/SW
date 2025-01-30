package de.othr.easytownhall.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class TemplateService {

    public String loadTemplate(String templateName, Map<String, String> placeholders) {
        try {
            String template = StreamUtils.copyToString(
                    getClass().getClassLoader().getResourceAsStream(templateName),
                    StandardCharsets.UTF_8
            );

            // Platzhalter ersetzen
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            return template;
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der E-Mail-Vorlage: " + templateName, e);
        }
    }

    public String loadTemplateSubscription(String templateName) {
        try {
            String template = StreamUtils.copyToString(
                    getClass().getClassLoader().getResourceAsStream(templateName),
                    StandardCharsets.UTF_8
            );

            return template;
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der E-Mail-Vorlage: " + templateName, e);
        }
    }
}
