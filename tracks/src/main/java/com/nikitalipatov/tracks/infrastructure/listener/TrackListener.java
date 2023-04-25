package com.nikitalipatov.tracks.infrastructure.listener;

import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.tracks.application.usecase.LoadTrack;
import com.nikitalipatov.tracks.application.usecase.RollbackTrack;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "result", groupId = "lastfm", containerFactory = "kafkaListenerContainerFactory")
@RequiredArgsConstructor

public class TrackListener {

    private final RollbackTrack rollbackTrack;
    private final LoadTrack loadTrack;

    @KafkaHandler
    public void trackHandler(KafkaMessage kafkaMessage) {
        switch (kafkaMessage.getEventType()) {
            case LOAD -> loadTrack.loadAllTracks(kafkaMessage.getArtistName(), kafkaMessage.getAlbumName());
            case ROLLBACK -> rollbackTrack.rollback(kafkaMessage.getAlbumId());
        }
    }
}
