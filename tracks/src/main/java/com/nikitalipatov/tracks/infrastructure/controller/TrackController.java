package com.nikitalipatov.tracks.infrastructure.controller;

import com.nikitalipatov.common.dto.GenreDto;
import com.nikitalipatov.common.dto.TrackDto;
import com.nikitalipatov.common.feign.TrackClient;
import com.nikitalipatov.tracks.application.usecase.FindTrack;
import com.nikitalipatov.tracks.application.usecase.LoadTrack;
import com.nikitalipatov.tracks.application.usecase.TrackChart;
import com.nikitalipatov.tracks.infrastructure.mapper.TrackConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/track")
public class TrackController implements TrackClient{

    private final TrackConverter trackConverter;
    private final FindTrack findTrack;
    private final TrackChart trackChart;
    private final LoadTrack loadTrack;

    @Override
    public TrackDto findTrack(String artistName, String trackName) {
        return trackConverter.toDto(findTrack.findTrack(artistName, trackName));
    }

    @Override
    public List<TrackDto> getTracksChart(String sortParameter) {
        return trackConverter.toDto(trackChart.createTracksChart(sortParameter));
    }

    @Override
    public List<TrackDto> getTracksByArtist(String artistName) {
        return trackConverter.toDto(findTrack.findTrack(artistName));
    }

    @Override
    public List<TrackDto> getTrackByGenre(GenreDto genreDto) {
        return trackConverter.toDto(findTrack.findTrack(genreDto.getGenreList()));
    }

    @Override
    public void loadOneTrack(String artistName, String trackName) {
        loadTrack.loadTrack(artistName, trackName);
    }

    @Override
    public void loadAllTracks(String artistName, String albumName) {
        loadTrack.loadAllTracks(artistName, albumName);
    }
}
