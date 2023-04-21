package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.OrchestratorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Album", url = "http://localhost:8081/api/album")
public interface AlbumClient {

    @GetMapping("/load/{artistName}/{albumName}")
    OrchestratorDto loadAlbum(@PathVariable String artistName, @PathVariable String albumName);
}
