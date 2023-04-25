package com.nikitalipatov.tracks.application.usecase;

public interface LoadTrack {

    void loadTrack(String artistName, String trackName);
    void loadAllTracks(String artistName, String albumName);
}
