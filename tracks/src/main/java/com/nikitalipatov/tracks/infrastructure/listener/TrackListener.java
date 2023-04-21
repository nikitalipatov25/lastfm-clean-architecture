package com.nikitalipatov.tracks.infrastructure.listener;

import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.tracks.application.usecase.LoadTrack;
import com.nikitalipatov.tracks.application.usecase.RollbackTrack;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.nikitalipatov.common.enums.EventType.ALBUM_SAVE;

@Component
@KafkaListener(topics = "trackCommand", groupId = "lastfm", containerFactory = "kafkaListenerContainerFactory")
@RequiredArgsConstructor
public class TrackListener {

    private final LoadTrack loadTrack;
    private final RollbackTrack rollbackTrack;

    @KafkaHandler
    public void trackHandler(KafkaMessage kafkaMessage) {
//        if (kafkaMessage.getEventType().equals(ALBUM_SAVE)) {
//            switch (kafkaMessage.getStatus()) {
//                case FAIL -> rollbackTrack.rollback(kafkaMessage.getArtistName(), kafkaMessage.getAlbumName());
//                case SUCCESS -> loadTrack.loadTrack(kafkaMessage.getArtistName(), kafkaMessage.getAlbumName(), kafkaMessage.getTrackList());
//            }
        rollbackTrack.rollback(kafkaMessage.getArtistName(), kafkaMessage.getAlbumName());

        }

//        if (kafkaMessage.getEventType().name().equals(ALBUM_SAVE.name()) && kafkaMessage.getStatus().name().equals(SUCCESS.name())) {
//            loadTrack.loadTrack(kafkaMessage.getArtistName(), kafkaMessage.getAlbumName(), kafkaMessage.getTrackList());
//        }
//        if (kafkaMessage.getEventType().name().equals(ALBUM_SAVE.name()) || kafkaMessage.getEventType().name().equals(ARTIST_SAVE.name())
//                && kafkaMessage.getStatus().name().equals(FAIL.name())) {
//            rollbackTrack.rollback(kafkaMessage.getArtistName(), kafkaMessage.getAlbumName());
//        }
    }
