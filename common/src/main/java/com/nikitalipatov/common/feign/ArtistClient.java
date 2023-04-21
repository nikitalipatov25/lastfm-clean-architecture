package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.OrchestratorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Artist", url = "http://localhost:8082/api/artist")
public interface ArtistClient {

    @GetMapping("/load/{artistName}")
    OrchestratorDto loadArtist(@PathVariable String artistName);
}
