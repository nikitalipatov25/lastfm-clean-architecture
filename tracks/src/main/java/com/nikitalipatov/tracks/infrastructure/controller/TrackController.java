package com.nikitalipatov.tracks.infrastructure.controller;

import com.nikitalipatov.tracks.application.usecase.FindTrack;
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
}
