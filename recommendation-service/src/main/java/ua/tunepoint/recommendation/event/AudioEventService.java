package ua.tunepoint.recommendation.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.tunepoint.audio.model.event.audio.AudioCreatedEvent;
import ua.tunepoint.audio.model.event.audio.AudioDeletedEvent;
import ua.tunepoint.audio.model.event.audio.AudioLikeEvent;
import ua.tunepoint.audio.model.event.audio.AudioListenEvent;
import ua.tunepoint.auth.model.event.user.UserRegisteredEvent;
import ua.tunepoint.event.starter.handler.DomainEventHandlers;
import ua.tunepoint.event.starter.handler.DomainEventHandlersBuilder;
import ua.tunepoint.event.starter.registry.DomainRegistry;
import ua.tunepoint.recommendation.service.ModelService;

import static ua.tunepoint.audio.model.event.Domain.AUDIO;
import static ua.tunepoint.auth.model.event.AuthDomain.AUTH;

@Service
@Profile("audio")
@RequiredArgsConstructor
public class AudioEventService {

    private final DomainRegistry domainRegistry;
    private final ModelService modelService;

    public DomainEventHandlers eventHandlers() {
        return DomainEventHandlersBuilder.withRegistry(domainRegistry)
                .forDomain(AUDIO.getName())
                    .onEvent(AudioCreatedEvent.class, this::handleAudioCreated)
                    .onEvent(AudioListenEvent.class, this::handleAudioListen)
                    .onEvent(AudioLikeEvent.class, this::handleAudioLike)
                    .onEvent(AudioDeletedEvent.class, this::handleAudioDeleted)
                .forDomain(AUTH.getName())
                    .onEvent(UserRegisteredEvent.class, this::handleUserCreated)
                .build();
    }

    private void handleAudioCreated(AudioCreatedEvent event) {
        modelService.addItem(event.getAudioId().intValue());
    }

    private void handleAudioDeleted(AudioDeletedEvent event) {
        modelService.deleteItem(event.getAudioId().intValue());
    }

    private void handleUserCreated(UserRegisteredEvent event) {
        modelService.addUser(event.getUserId().intValue());
    }

    private void handleAudioListen(AudioListenEvent event) {
        modelService.update(event.getAudioId().intValue(), event.getUserId().intValue());
    }

    private void handleAudioLike(AudioLikeEvent event) {
        modelService.update(event.getAudioId().intValue(), event.getUserId().intValue());
    }
}
