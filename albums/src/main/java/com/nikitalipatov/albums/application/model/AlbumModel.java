package com.nikitalipatov.albums.application.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumModel {
    private String id;
    private String name;
    private String artist;
    private String artistId;
    private String releaseDate;
    private int playCount;
    private int listeners;
    private List<String> tags; // array? genre?
    private List<String> trackIdsList;
}
