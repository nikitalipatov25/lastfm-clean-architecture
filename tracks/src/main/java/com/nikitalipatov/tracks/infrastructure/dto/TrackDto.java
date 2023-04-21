package com.nikitalipatov.tracks.infrastructure.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackDto {

    private String id;
    private String name;
    private int playCount;
    private int listeners;
    private String artistId;
    private String albumId;
    private List<String> tagsList;
}
