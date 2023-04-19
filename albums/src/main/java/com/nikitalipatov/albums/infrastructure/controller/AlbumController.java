package com.nikitalipatov.albums.infrastructure.controller;

import com.nikitalipatov.albums.application.usecase.LoadAlbumFromLastFm;
import com.nikitalipatov.albums.infrastructure.db.mapper.AlbumMapper;
import com.nikitalipatov.albums.infrastructure.dto.AlbumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/album")
public class AlbumController {

    private final LoadAlbumFromLastFm loadAlbumFromLastFm;
    private final AlbumMapper albumMapper;

    @GetMapping(value = "/load/{artistName}/{albumName}")
    public AlbumDto loadAlbumFromLastFm(@PathVariable String artistName, @PathVariable String albumName) {
        return albumMapper.toDto(loadAlbumFromLastFm.getAlbumWithTracksFromLastFm(artistName, albumName));
    }
}
