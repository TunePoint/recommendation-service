package ua.tunepoint.recommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import ua.tunepoint.recommendation.config.properties.ModelInitializerProperties;
import ua.tunepoint.recommendation.config.properties.ModelProperties;

@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
@EnableConfigurationProperties({ModelInitializerProperties.class, ModelProperties.class})
public class ServiceStarter {

    public static void main(String[] args) {
        SpringApplication.run(ServiceStarter.class, args);
    }

}
