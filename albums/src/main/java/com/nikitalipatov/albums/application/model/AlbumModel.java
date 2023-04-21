package com.nikitalipatov.albums.application.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumModel implements Serializable {
    private String id;
    private String name;
    private String artistId;
    private int playCount;
    private int listeners;
}
