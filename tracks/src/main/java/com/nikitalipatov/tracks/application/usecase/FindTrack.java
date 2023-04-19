package com.nikitalipatov.tracks.application.usecase;

import com.nikitalipatov.tracks.application.model.TrackModel;

public interface FindTrack {

    TrackModel findTrack(String artistName, String trackName);
}
