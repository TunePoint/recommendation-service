package ua.tunepoint.recommendation.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.tunepoint.recommendation.service.ModelService;
import ua.tunepoint.audio.model.event.playlist.PlaylistCreatedEvent;
import ua.tunepoint.audio.model.event.playlist.PlaylistLikedEvent;
import ua.tunepoint.auth.model.event.user.UserRegisteredEvent;
import ua.tunepoint.event.starter.handler.DomainEventHandlers;
import ua.tunepoint.event.starter.handler.DomainEventHandlersBuilder;
import ua.tunepoint.event.starter.registry.DomainRegistry;

import static ua.tunepoint.audio.model.event.Domain.PLAYLIST;
import static ua.tunepoint.auth.model.event.AuthDomain.AUTH;

@Service
@Profile("playlist")
@RequiredArgsConstructor
public class PlaylistEventService {

    private final DomainRegistry domainRegistry;
    private final ModelService modelService;

    public DomainEventHandlers eventHandlers() {
        return DomainEventHandlersBuilder.withRegistry(domainRegistry)
                .forDomain(PLAYLIST.getName())
                    .onEvent(PlaylistCreatedEvent.class, this::handlePlaylistCreated)
                    .onEvent(PlaylistLikedEvent.class, this::handlePlaylistLike)
                .forDomain(AUTH.getName())
                    .onEvent(UserRegisteredEvent.class, this::handleUserCreated)
                .build();
    }

    private void handlePlaylistCreated(PlaylistCreatedEvent event) {
        modelService.addItem(event.getPlaylistId().intValue());
    }

    private void handleUserCreated(UserRegisteredEvent event) {
        modelService.addUser(event.getUserId().intValue());
    }

    private void handlePlaylistLike(PlaylistLikedEvent event) {
        modelService.update(event.getPlaylistId().intValue(), event.getUserId().intValue());
    }
}