package ua.tunepoint.recommendation.config.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.tunepoint.recommendation.event.PlaylistEventService;
import ua.tunepoint.audio.model.event.PlaylistEventType;
import ua.tunepoint.auth.model.event.AuthEventType;
import ua.tunepoint.event.starter.DomainRelation;
import ua.tunepoint.event.starter.handler.DomainEventHandlers;
import ua.tunepoint.event.starter.registry.DomainRegistry;
import ua.tunepoint.event.starter.registry.builder.DomainRegistryBuilder;

import java.util.Set;

import static ua.tunepoint.audio.model.event.Domain.PLAYLIST;
import static ua.tunepoint.auth.model.event.AuthDomain.AUTH;

@Configuration
@Profile("playlist")
public class PlaylistEventConfiguration {

    @Bean
    public DomainRegistry domainRegistry() {
        return new DomainRegistryBuilder()
                .register(PLAYLIST.getName(), PlaylistEventType.values(), Set.of(DomainRelation.CONSUMER))
                .register(AUTH.getName(), AuthEventType.values(), Set.of(DomainRelation.CONSUMER))
                .build();
    }

    @Bean
    public DomainEventHandlers eventHandlers(PlaylistEventService service) {
        return service.eventHandlers();
    }
}
