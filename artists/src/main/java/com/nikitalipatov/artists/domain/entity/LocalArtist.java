package com.nikitalipatov.artists.domain.entity;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class LocalArtist {

    private final String id;
    private final String name;
    private final int playCount;
    private final int listeners;
    private final List<String> tags;

    private LocalArtist(String id, String name, int playCount, int listeners, List<String> tags) {
        this.id = id;
        this.name = name;
        this.playCount = playCount;
        this.listeners = listeners;
        this.tags = tags;
    }

    public static LocalArtist of(String id, String name, int playCount, int listeners, List<String> genre) {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        return new LocalArtist(id, name, playCount, listeners, genre);
    }
}
