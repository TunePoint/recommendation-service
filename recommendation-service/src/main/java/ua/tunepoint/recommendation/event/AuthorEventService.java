package ua.tunepoint.recommendation.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.model.event.user.UserFollowedEvent;
import ua.tunepoint.audio.model.event.audio.AudioLikeEvent;
import ua.tunepoint.audio.model.event.audio.AudioListenEvent;
import ua.tunepoint.audio.model.event.playlist.PlaylistLikedEvent;
import ua.tunepoint.auth.model.event.user.UserRegisteredEvent;
import ua.tunepoint.event.starter.handler.DomainEventHandlers;
import ua.tunepoint.event.starter.handler.DomainEventHandlersBuilder;
import ua.tunepoint.event.starter.registry.DomainRegistry;
import ua.tunepoint.recommendation.service.ModelService;

import static ua.tunepoint.account.model.event.AccountDomain.USER;
import static ua.tunepoint.audio.model.event.Domain.AUDIO;
import static ua.tunepoint.audio.model.event.Domain.PLAYLIST;
import static ua.tunepoint.auth.model.event.AuthDomain.AUTH;

@Service
@Profile("author")
@RequiredArgsConstructor
public class AuthorEventService {

    private final DomainRegistry domainRegistry;
    private final ModelService modelService;

    public DomainEventHandlers eventHandlers() {
        return DomainEventHandlersBuilder.withRegistry(domainRegistry)
                .forDomain(PLAYLIST.getName())
                    .onEvent(PlaylistLikedEvent.class, this::handlePlaylistLike)
                .forDomain(AUTH.getName())
                    .onEvent(UserRegisteredEvent.class, this::handleUserCreated)
                .forDomain(USER.getName())
                    .onEvent(UserFollowedEvent.class, this::handleUserFollow)
                .forDomain(AUDIO.getName())
                    .onEvent(AudioLikeEvent.class, this::handleAudioLiked)
                    .onEvent(AudioListenEvent.class, this::handleAudioListen)
                .build();
    }

    public void handleUserFollow(UserFollowedEvent event) {
        modelService.update(event.getFollowerId().intValue(), event.getUserId().intValue());
    }

    public void handleAudioListen(AudioListenEvent event) {
        modelService.update(event.getAudioId().intValue(), event.getUserId().intValue());
    }

    public void handleAudioLiked(AudioLikeEvent event) {
        modelService.update(event.getUserId().intValue(), event.getAudioOwnerId().intValue());
    }

    public void handlePlaylistLike(PlaylistLikedEvent event) {
        modelService.update(event.getUserId().intValue(), event.getPlaylistOwnerId().intValue());
    }

    public void handleUserCreated(UserRegisteredEvent event) {
        modelService.addUser(event.getUserId().intValue());
        modelService.addItem(event.getUserId().intValue());
    }
}
