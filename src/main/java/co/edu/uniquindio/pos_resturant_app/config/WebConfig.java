package co.edu.uniquindio.pos_resturant_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                "http://localhost:80",
                                "http://localhost:4200",
                                "http://45.55.149.182",
                                "http://45.55.149.182:80",
                                "http://127.0.0.1:80",
                                "http://127.0.0.1:4200"
                        ) // or whatever your front IP/domain is
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true); // ⚠️ REQUIRED if frontend sends cookies or authorization headers
            }
        };
    }
}
