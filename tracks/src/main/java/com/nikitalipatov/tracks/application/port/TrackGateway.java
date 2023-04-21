package com.nikitalipatov.tracks.application.port;

import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.tracks.application.model.TrackModel;

import java.util.List;

public interface TrackGateway {

    TrackModel save(TrackModel trackModel);

    TrackModel getTrackInfo(String artistName, String trackName);

    List<TrackModel> getTracksByArtist(String artistName);

    List<TrackModel> getTracksByGenre(List<String> genreList);

    List<TrackModel> getTrackByListeners();

    List<TrackModel> getTrackByPlayCount();

    void sendInfo(KafkaMessage kafkaMessage);

    boolean isTrackExists(String trackName, String artistName);

    void deleteTrack(String artistName, String trackName);

}
