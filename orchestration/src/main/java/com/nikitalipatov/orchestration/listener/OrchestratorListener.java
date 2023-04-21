package com.nikitalipatov.orchestration.listener;

import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.orchestration.service.OrchestrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.nikitalipatov.common.enums.EventType.ALBUM_SAVE;

@Component
@KafkaListener(topics = "result", groupId = "lastfm", containerFactory = "kafkaListenerContainerFactory")
@RequiredArgsConstructor
public class OrchestratorListener {

    private final OrchestrationService orchestrationService;

    @KafkaHandler
    public void trackHandler(KafkaMessage kafkaMessage) {
        switch (kafkaMessage.getEventType()) {
            case TRACK_SAVE -> orchestrationService.LoadTrackSteps(kafkaMessage.getArtistName(), kafkaMessage.getAlbumName());
            case ALBUM_LOAD -> orchestrationService.LoadAlbumSteps(kafkaMessage.getArtistName(), kafkaMessage.getAlbumName(), kafkaMessage.getTrackList());
        }
    }
}
