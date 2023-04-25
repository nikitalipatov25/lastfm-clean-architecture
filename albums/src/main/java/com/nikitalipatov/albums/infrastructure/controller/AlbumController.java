package com.nikitalipatov.albums.infrastructure.controller;

import com.nikitalipatov.albums.application.usecase.FindAlbum;
import com.nikitalipatov.albums.application.usecase.LoadAlbum;
import com.nikitalipatov.albums.infrastructure.mapper.AlbumMapper;
import com.nikitalipatov.common.dto.AlbumDto;
import com.nikitalipatov.common.feign.AlbumClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/album")
public class AlbumController implements AlbumClient {

    private final FindAlbum findAlbum;
    private final LoadAlbum loadAlbum;
    private final AlbumMapper albumMapper;

    @Override
    public AlbumDto findAlbum(String artistName, String albumName) {
        return albumMapper.toDto(findAlbum.findAlbum(artistName, albumName));
    }

    @Override
    public void loadAlbum(String artistName, String albumName, String albumId) {
        loadAlbum.loadAlbum(artistName, albumName, albumId);
    }

}
