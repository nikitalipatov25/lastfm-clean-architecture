package com.nikitalipatov.artists.infrastructure.listener;

import com.nikitalipatov.artists.application.usecase.FindArtist;
import com.nikitalipatov.common.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "command", groupId = "lastfm", containerFactory = "kafkaListenerContainerFactory")
@RequiredArgsConstructor
public class ArtistListener {

    private final FindArtist findArtist;

    @KafkaHandler
    public void artistHandler(KafkaMessage kafkaMessage) {
        findArtist.findArtist(kafkaMessage.getArtistName());
    }
}
