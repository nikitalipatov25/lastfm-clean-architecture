package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.GenreDto;
import com.nikitalipatov.common.dto.TrackDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "Track", url = "http://localhost:8083/api/track")
public interface TrackClient {

    @GetMapping(value = "/find/{artistName}/{trackName}")
    TrackDto findTrack(@PathVariable String artistName, @PathVariable String trackName);

    @GetMapping(value = "/chart/{sortParameter}")
    List<TrackDto> getTracksChart(@PathVariable String sortParameter);

    @GetMapping(value = "/list/{artistName}")
    List<TrackDto> getTracksByArtist(@PathVariable String artistName);

    @PostMapping(value = "/list")
    List<TrackDto> getTrackByGenre(@RequestBody GenreDto genreDto);

    @GetMapping(value = "/load/{artistName}/{trackName}")
    void loadOneTrack(@PathVariable String artistName, @PathVariable String trackName);

    @PostMapping(value = "/load/all/{artistName}/{albumName}")
    void loadAllTracks(@PathVariable String artistName, @PathVariable String albumName);
}
