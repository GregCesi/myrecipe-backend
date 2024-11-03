package myrecipe.fr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Autorise les requêtes depuis ce domaine
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Autorise ces méthodes HTTP
                .allowedHeaders("*"); // Autorise tous les en-têtes
    }
}
