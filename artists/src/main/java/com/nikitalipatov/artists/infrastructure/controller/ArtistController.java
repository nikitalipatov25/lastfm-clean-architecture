package com.nikitalipatov.artists.infrastructure.controller;

import com.nikitalipatov.artists.application.usecase.CreateChartOfArtists;
import com.nikitalipatov.artists.application.usecase.RequestArtist;
import com.nikitalipatov.artists.infrastructure.db.mapper.ArtistMapper;
import com.nikitalipatov.artists.infrastructure.dto.ArtistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/artist")
public class ArtistController {

    private final RequestArtist requestArtist;
    private final CreateChartOfArtists createChartOfArtists;
    private final ArtistMapper artistMapper;

    @GetMapping(value = "/get/{artistName}")
    public ArtistDto searchArtist(@PathVariable String artistName) {
        return artistMapper.toDto(requestArtist.getArtistFomLastFm(artistName));
    }

    @GetMapping(value = "/chart/{sortParameter}")
    public List<ArtistDto> getArtistsChartSortedBy(@PathVariable String sortParameter) {
        return artistMapper.toDto(createChartOfArtists.createChartOfLocalArtist(sortParameter));
    }

}
