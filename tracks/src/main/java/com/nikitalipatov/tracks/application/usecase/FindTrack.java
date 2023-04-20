package com.nikitalipatov.tracks.application.usecase;

import com.nikitalipatov.tracks.application.model.TrackModel;

import java.util.List;

public interface FindTrack {

    TrackModel findTrack(String artistName, String trackName);

    List<TrackModel> findTrack(String artistName);

    List<TrackModel> findTrack(List<String> genres);
}
