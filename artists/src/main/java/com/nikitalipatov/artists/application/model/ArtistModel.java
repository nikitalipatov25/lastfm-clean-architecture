package com.nikitalipatov.artists.application.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistModel implements Serializable {
    private String id;
    private String name;
    private int playCount;
    private int listeners;
}
