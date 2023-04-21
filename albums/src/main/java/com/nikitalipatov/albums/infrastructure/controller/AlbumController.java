package com.nikitalipatov.albums.infrastructure.controller;

import com.nikitalipatov.albums.application.usecase.FindAlbum;
import com.nikitalipatov.albums.application.usecase.LoadAlbum;
import com.nikitalipatov.albums.infrastructure.db.mapper.AlbumMapper;
import com.nikitalipatov.albums.infrastructure.dto.AlbumDto;
import com.nikitalipatov.common.dto.OrchestratorDto;
import com.nikitalipatov.common.feign.AlbumClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/album")
public class AlbumController implements AlbumClient {

    private final FindAlbum findAlbum;
    private final AlbumMapper albumMapper;
    private final LoadAlbum loadAlbum;

    @GetMapping(value = "/load2/{artistName}/{albumName}")
    public AlbumDto loadAlbumFromLastFm(@PathVariable String artistName, @PathVariable String albumName) {
        return albumMapper.toDto(findAlbum.findAlbum(artistName, albumName));
    }

    @Override
    public OrchestratorDto loadAlbum(String artistName, String albumName) {
        return loadAlbum.loadAlbum(artistName, albumName);
    }
}
