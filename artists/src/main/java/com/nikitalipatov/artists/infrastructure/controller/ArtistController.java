package com.nikitalipatov.artists.infrastructure.controller;

import com.nikitalipatov.artists.application.usecase.ArtistChart;
import com.nikitalipatov.artists.application.usecase.FindArtist;
import com.nikitalipatov.artists.application.usecase.LoadArtist;
import com.nikitalipatov.artists.infrastructure.db.mapper.ArtistMapper;
import com.nikitalipatov.artists.infrastructure.dto.ArtistDto;
import com.nikitalipatov.common.dto.OrchestratorDto;
import com.nikitalipatov.common.feign.ArtistClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/artist")
public class ArtistController implements ArtistClient {

    private final FindArtist findArtist;
    private final LoadArtist loadArtist;
    private final ArtistChart artistChart;
    private final ArtistMapper artistMapper;

    @GetMapping(value = "/get/{artistName}")
    public ArtistDto searchArtist(@PathVariable String artistName) {
        return artistMapper.toDto(findArtist.findArtist(artistName));
    }

    @GetMapping(value = "/chart/{sortParameter}")
    public List<ArtistDto> getArtistsChartSortedBy(@PathVariable String sortParameter) {
        return artistMapper.toDto(artistChart.createArtistsChart(sortParameter));
    }

    @Override
    public OrchestratorDto loadArtist(String artistName) {
        return loadArtist.loadArtist(artistName);
    }
}
