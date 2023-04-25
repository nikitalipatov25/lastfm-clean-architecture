package com.nikitalipatov.tracks.application.port;

import com.nikitalipatov.tracks.application.model.TrackModel;

import java.util.List;

public interface TrackGateway {

    TrackModel save(TrackModel trackModel);

    TrackModel getTrackInfo(String trackId);

    List<TrackModel> getTracksByArtist(String artistId);

    List<TrackModel> getTracksByGenre(List<String> genreList);

    List<TrackModel> getTrackByListeners();

    List<TrackModel> getTrackByPlayCount();

    void sendToAlbum(String artistName, String albumName, String albumId);

    boolean isTrackExists(String trackId);

    void rollbackTrack(String albumId);

}
