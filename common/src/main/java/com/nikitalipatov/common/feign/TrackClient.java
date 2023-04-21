package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.OrchestratorDto;
import com.nikitalipatov.common.dto.SomeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "Track", url = "http://localhost:8083/api/track")
public interface TrackClient {

    @PostMapping(value = "/load")
    OrchestratorDto loadTrack(@RequestBody SomeDto someDto);
}
