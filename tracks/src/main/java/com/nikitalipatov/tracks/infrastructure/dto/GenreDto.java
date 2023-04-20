package com.nikitalipatov.tracks.infrastructure.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    private List<String> genreList;
}
