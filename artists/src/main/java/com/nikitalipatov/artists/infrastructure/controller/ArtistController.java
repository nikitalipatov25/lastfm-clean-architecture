package com.nikitalipatov.artists.infrastructure.controller;

import com.nikitalipatov.artists.application.usecase.ArtistChart;
import com.nikitalipatov.artists.application.usecase.FindArtist;
import com.nikitalipatov.artists.application.usecase.LoadArtist;
import com.nikitalipatov.artists.infrastructure.mapper.ArtistConverter;
import com.nikitalipatov.common.dto.ArtistDto;
import com.nikitalipatov.common.feign.ArtistClient;
import lombok.RequiredArgsConstructor;
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
    private final ArtistConverter artistConverter;

    @Override
    public void loadArtist(String artistName, String albumId) {
        loadArtist.loadArtist(artistName, albumId);
    }

    @Override
    public ArtistDto findArtist(String artistName) {
        return artistConverter.toDto(findArtist.findArtist(artistName));
    }

    @Override
    public List<ArtistDto> getArtistsChartSortedBy(String sortParameter) {
        return artistConverter.toDto(artistChart.createArtistsChart(sortParameter));
    }
}
