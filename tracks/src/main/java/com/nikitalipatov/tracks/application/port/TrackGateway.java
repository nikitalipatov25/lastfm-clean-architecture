package com.nikitalipatov.tracks.application.port;

import com.nikitalipatov.tracks.application.model.TrackModel;

import java.util.List;

public interface TrackGateway {

    TrackModel save(TrackModel trackModel);

    TrackModel getTrackInfo(String artistName, String trackName);

    List<TrackModel> getTracksByArtist(String artistName);

    List<TrackModel> getTracksByGenre(List<String> genreList);

    List<TrackModel> getTrackByListeners();

    List<TrackModel> getTrackByPlayCount();

}
