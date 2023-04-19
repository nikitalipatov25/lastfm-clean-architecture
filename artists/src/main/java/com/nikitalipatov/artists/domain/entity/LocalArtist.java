package com.nikitalipatov.artists.domain.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class LocalArtist {

    // TODO: 19.04.2023 проверка на null
    // TODO: 19.04.2023 артист еще должен содержать ссылки на изображение и прочее

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
        return new LocalArtist(id, name, playCount, listeners, genre);
    }
}
