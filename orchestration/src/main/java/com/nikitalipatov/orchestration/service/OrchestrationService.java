package com.nikitalipatov.orchestration.service;

import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.common.dto.SomeDto;
import com.nikitalipatov.common.feign.AlbumClient;
import com.nikitalipatov.common.feign.ArtistClient;
import com.nikitalipatov.common.feign.TrackClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nikitalipatov.common.enums.EventType.ALBUM_LOAD;
import static com.nikitalipatov.common.enums.EventType.TRACK_LOAD;
import static com.nikitalipatov.common.enums.Status.FAIL;

@Service
@RequiredArgsConstructor
public class OrchestrationService {

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    //private final OrchestratorRepository orchestratorRepository;
    private final TrackClient trackClient;
    private final AlbumClient albumClient;
    private final ArtistClient artistClient;

    public void LoadTrackSteps(String artistName, String albumName) {
        try {
            if (albumClient.loadAlbum(artistName, albumName).getAlbumStatus().equals(FAIL.name())) {
                kafkaTemplate.send("trackCommand", KafkaMessage.builder()
                        .eventType(TRACK_LOAD)
                        .status(FAIL)
                        .artistName(artistName)
                        .albumName(albumName)
                        .build());
            } else if (artistClient.loadArtist(artistName).getArtistStatus().equals(FAIL.name())) {
                kafkaTemplate.send("trackCommand", KafkaMessage.builder()
                        .eventType(TRACK_LOAD)
                        .status(FAIL)
                        .artistName(artistName)
                        .albumName(albumName)
                        .build());
                kafkaTemplate.send("albumCommand", KafkaMessage.builder()
                        .eventType(TRACK_LOAD)
                        .status(FAIL)
                        .artistName(artistName)
                        .albumName(albumName)
                        .build());
            } else {
                System.out.println("SUCCESS!");
            }
        } catch (Exception e) {
            kafkaTemplate.send("trackCommand", KafkaMessage.builder()
                    .eventType(TRACK_LOAD)
                    .status(FAIL)
                    .artistName(artistName)
                    .albumName(albumName)
                    .build());
            kafkaTemplate.send("albumCommand", KafkaMessage.builder()
                    .eventType(TRACK_LOAD)
                    .status(FAIL)
                    .artistName(artistName)
                    .albumName(albumName)
                    .build());
        }

//        if (albumClient.loadAlbum(artistName, albumName).getAlbumStatus().equals(FAIL.name())) {
//            kafkaTemplate.send("trackCommand", KafkaMessage.builder()
//                    .eventType(TRACK_LOAD)
//                    .status(FAIL)
//                    .artistName(artistName)
//                    .albumName(albumName)
//                    .build());
//        } else if (artistClient.loadArtist(artistName).getArtistStatus().equals(FAIL.name())) {
//            kafkaTemplate.send("trackCommand", KafkaMessage.builder()
//                    .eventType(TRACK_LOAD)
//                    .status(FAIL)
//                    .artistName(artistName)
//                    .albumName(albumName)
//                    .build());
//            kafkaTemplate.send("albumCommand", KafkaMessage.builder()
//                    .eventType(TRACK_LOAD)
//                    .status(FAIL)
//                    .artistName(artistName)
//                    .albumName(albumName)
//                    .build());
//        } else {
//            System.out.println("SUCCESS!");
//        }
    }

    public void LoadAlbumSteps(String artistName, String albumName, List<String> tracksList) {
        var temp = SomeDto.builder()
                .albumName(albumName)
                .artistName(artistName)
                .tracksList(tracksList)
                .build();
        if (trackClient.loadTrack(temp).getTrackStatus().equals(FAIL.name())) {
            kafkaTemplate.send("albumCommand", KafkaMessage.builder()
                    .eventType(ALBUM_LOAD)
                    .status(FAIL)
                    .artistName(artistName)
                    .albumName(albumName)
                    .build());
        } else if (artistClient.loadArtist(artistName).getArtistStatus().equals(FAIL.name())) {
            kafkaTemplate.send("albumCommand", KafkaMessage.builder()
                    .eventType(ALBUM_LOAD)
                    .status(FAIL)
                    .artistName(artistName)
                    .albumName(albumName)
                    .build());
            kafkaTemplate.send("trackCommand", KafkaMessage.builder()
                    .eventType(ALBUM_LOAD)
                    .status(FAIL)
                    .artistName(artistName)
                    .albumName(albumName)
                    .build());
        } else {
            System.out.println("SUCCESS!!");
        }
    }
}
