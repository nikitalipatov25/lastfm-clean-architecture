package com.nikitalipatov.common.dto;

import lombok.*;

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
