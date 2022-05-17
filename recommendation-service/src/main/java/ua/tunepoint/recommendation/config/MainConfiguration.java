package ua.tunepoint.recommendation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.tunepoint.web.exception.WebExceptionHandler;

@Configuration
public class MainConfiguration {

    @Bean
    public WebExceptionHandler exceptionHandler() {
        return new WebExceptionHandler();
    }
}
