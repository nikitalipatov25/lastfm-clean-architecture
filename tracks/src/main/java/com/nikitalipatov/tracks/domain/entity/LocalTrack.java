package com.nikitalipatov.tracks.domain.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class LocalTrack {

    private final String id;
    private final String name;
    private final int duration;
    private final int playCount;
    private final int listeners;
    private final String artist;
    private final String artistId;
    private final String album;
    private final String albumId;
    private final List<String> tagsList;

    private LocalTrack(String id, String name, int duration, int playCount, int listeners, String artist, String artistId, String album, String albumId, List<String> tagsList) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.playCount = playCount;
        this.listeners = listeners;
        this.artist = artist;
        this.artistId = artistId;
        this.album = album;
        this.albumId = albumId;
        this.tagsList = tagsList;
    }

    public static LocalTrack of(String id, String name, int duration, int playCount, int listeners, String artist, String artistId, String album, String albumId, List<String> tagsList) {
        return new LocalTrack(id, name, duration, playCount, listeners, artist, artistId, album, albumId, tagsList);
    }
}
