package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.AlbumDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Album", url = "http://localhost:8081/api/album")
public interface AlbumClient {

    @GetMapping(value = "/find/{artistName}/{albumName}")
    AlbumDto findAlbum(@PathVariable String artistName, @PathVariable String albumName);

    @GetMapping(value = "/load/{artistName}/{albumName}/{albumId}")
    void loadAlbum(@PathVariable String artistName, @PathVariable String albumName, @PathVariable String albumId);
}
