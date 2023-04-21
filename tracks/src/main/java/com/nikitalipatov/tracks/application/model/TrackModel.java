package com.nikitalipatov.tracks.application.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrackModel implements Serializable {

    private String id;
    private String name;
    private int playCount;
    private int listeners;
    private String artistId;
    private String albumId;
    private List<String> tagsList;
}
