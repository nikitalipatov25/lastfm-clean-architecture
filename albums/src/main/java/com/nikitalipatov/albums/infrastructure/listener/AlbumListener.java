package com.nikitalipatov.albums.infrastructure.listener;

import com.nikitalipatov.albums.application.usecase.RollbackAlbum;
import com.nikitalipatov.common.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "result", groupId = "lastfm", containerFactory = "kafkaListenerContainerFactory")
@RequiredArgsConstructor
public class AlbumListener {

    private final RollbackAlbum rollbackAlbum;

    @KafkaHandler
    public void albumHandler(KafkaMessage kafkaMessage) {
        rollbackAlbum.rollback(kafkaMessage.getArtistName(), kafkaMessage.getAlbumName());
    }
}
