package com.nikitalipatov.albums.domain.entity;

import lombok.Getter;

@Getter
public class LocalAlbum {

    private final String id;
    private final String name;
    private final String artistId;
    private final int playCount;
    private final int listeners;

    private LocalAlbum(String id, String name, String artistId, int playCount, int listeners) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
        this.playCount = playCount;
        this.listeners = listeners;
    }

    public static LocalAlbum of(String id, String name,  String artistId, int playCount, int listeners) {
        return new LocalAlbum(id, name, artistId, playCount, listeners);
    }
}
