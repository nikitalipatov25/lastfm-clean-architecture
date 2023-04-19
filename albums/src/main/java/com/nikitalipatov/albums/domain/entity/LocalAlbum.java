package com.nikitalipatov.albums.domain.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class LocalAlbum {

    private final String id;
    private final String name;
    private final String artist;
//    private final String artistId; // artistID нет в ответе с апи - убрать
//    private final String releaseDate;
    private final int playCount;
    private final int listeners;
    private final List<String> tagsList; // array? genre?
    private final List<String> trackNames;

    private LocalAlbum(String id, String name, String artist, int playCount, int listeners, List<String> tagsList, List<String> trackNames) {
        this.id = id;
        this.name = name;
        this.artist = artist;
//        this.artistId = artistId;
//        this.releaseDate = releaseDate;
        this.playCount = playCount;
        this.listeners = listeners;
        this.tagsList = tagsList;
        this.trackNames = trackNames;
    }

    public static LocalAlbum of(String id, String name, String artist, int playCount, int listeners, List<String> tags, List<String> trackIdsList) {
        return new LocalAlbum(id, name, artist, playCount, listeners, tags, trackIdsList);
    }
}
