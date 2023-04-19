package com.nikitalipatov.albums.infrastructure.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDto {

    private String id;
    private String name;
    private String artist;
    private String artistId;
    private String releaseDate;
    private int playCount;
    private int listeners;
    private List<String> trackIds;
    private List<String> tagsList;
}
