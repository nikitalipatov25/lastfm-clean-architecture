package com.nikitalipatov.albums.infrastructure.listener;

import com.nikitalipatov.albums.application.usecase.LoadAlbum;
import com.nikitalipatov.albums.application.usecase.RollbackAlbum;
import com.nikitalipatov.common.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "command", groupId = "lastfm", containerFactory = "kafkaListenerContainerFactory")
public class AlbumListener {

    private final RollbackAlbum rollbackAlbum;
    private final LoadAlbum loadAlbum;

    @KafkaHandler
    public void albumLoadHandler(KafkaMessage kafkaMessage) {
        switch (kafkaMessage.getEventType()) {
            case LOAD -> loadAlbum.loadAlbum(kafkaMessage.getArtistName(), kafkaMessage.getAlbumName(), kafkaMessage.getAlbumId());
            case ROLLBACK -> rollbackAlbum.rollback(kafkaMessage.getAlbumId());
        }
    }
}
