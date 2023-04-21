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
    private String artistId;
    private int playCount;
    private int listeners;
}
