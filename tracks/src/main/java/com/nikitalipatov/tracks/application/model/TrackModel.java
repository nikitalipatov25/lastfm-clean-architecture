package com.nikitalipatov.tracks.application.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackModel {

    private String id;
    private String name;
    private int duration;
    private int playCount;
    private int listeners;
    private String artist;
    private String artistId;
    private String album;
    private String albumId;
    private List<String> tagsList;
}
