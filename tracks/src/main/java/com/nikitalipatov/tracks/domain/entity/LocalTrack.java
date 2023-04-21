package com.nikitalipatov.tracks.domain.entity;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
// TODO: 20.04.2023 проверка на null
public class LocalTrack {

    // artist i album могут не понадобиться, только айди
    private final String id;
    private final String name;
    private final int playCount;
    private final int listeners;
    private final String artistId;
    private final String albumId;
    private final List<String> tagsList;

    private LocalTrack(String id, String name, int playCount, int listeners,
                       String artistId, String albumId, List<String> tagsList) {
        this.id = id;
        this.name = name;
        this.playCount = playCount;
        this.listeners = listeners;
        this.artistId = artistId;
        this.albumId = albumId;
        this.tagsList = tagsList;
    }

    public static LocalTrack of(String id, String name, int playCount, int listeners,
                                String artistId, String albumId, List<String> tagsList) {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        return new LocalTrack(id, name, playCount, listeners, artistId, albumId, tagsList);
    }
}
