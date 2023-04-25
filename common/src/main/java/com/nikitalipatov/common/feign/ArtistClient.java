package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.ArtistDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Artist", url = "http://localhost:8082/api/artist")
public interface ArtistClient {

    @GetMapping("/load/{artistName}/{albumId}")
    void loadArtist(@PathVariable String artistName, @PathVariable String albumId);

    @GetMapping(value = "/find/{artistName}")
    ArtistDto findArtist(@PathVariable String artistName);

    @GetMapping(value = "/chart/{sortParameter}")
    List<ArtistDto> getArtistsChartSortedBy(@PathVariable String sortParameter);
}
