package com.nikitalipatov.tracks.infrastructure.controller;

import com.nikitalipatov.common.dto.OrchestratorDto;
import com.nikitalipatov.common.dto.SomeDto;
import com.nikitalipatov.common.feign.TrackClient;
import com.nikitalipatov.tracks.application.usecase.FindTrack;
import com.nikitalipatov.tracks.application.usecase.LoadTrack;
import com.nikitalipatov.tracks.application.usecase.TrackChart;
import com.nikitalipatov.tracks.infrastructure.db.mapper.TrackMapper;
import com.nikitalipatov.tracks.infrastructure.dto.GenreDto;
import com.nikitalipatov.tracks.infrastructure.dto.TrackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/track")
public class TrackController {

    private final TrackMapper trackMapper;
    private final FindTrack findTrack;
    private final TrackChart trackChart;
    private final LoadTrack loadTrack;

    @GetMapping(value = "/get/{artistName}/{trackName}")
    public TrackDto searchTrack(@PathVariable String artistName, @PathVariable String trackName) {
        return trackMapper.toDto(findTrack.findTrack(artistName, trackName));
    }

    @GetMapping(value = "/list/{artistName}")
    public List<TrackDto> getTracksByArtist(@PathVariable String artistName) {
        return trackMapper.toDto(findTrack.findTrack(artistName));
    }

    @PostMapping(value = "/list")
    public List<TrackDto> getTrackByGenre(@RequestBody GenreDto genreDto) {
        return trackMapper.toDto(findTrack.findTrack(genreDto.getGenreList()));
    }

    @GetMapping(value = "/chart/{sortParameter}")
    public List<TrackDto> getTracksChart(@PathVariable String sortParameter) {
        return trackMapper.toDto(trackChart.createTracksChart(sortParameter));
    }

    @PostMapping(value = "/load")
    public OrchestratorDto loadTrack(@RequestBody SomeDto someDto) {
        return loadTrack.loadTrack(someDto.getArtistName(), someDto.getAlbumName(), someDto.getTracksList());
    }
}
