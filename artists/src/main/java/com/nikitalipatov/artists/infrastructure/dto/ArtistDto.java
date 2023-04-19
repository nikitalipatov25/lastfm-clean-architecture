package com.nikitalipatov.artists.infrastructure.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDto {
    private String id;
    private String name;
    private int playCount;
    private int listeners;
}
