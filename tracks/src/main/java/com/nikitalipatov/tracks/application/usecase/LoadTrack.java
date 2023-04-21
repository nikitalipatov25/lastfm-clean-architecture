package com.nikitalipatov.tracks.application.usecase;

import com.nikitalipatov.common.dto.OrchestratorDto;

import java.util.List;

public interface LoadTrack {

    OrchestratorDto loadTrack(String artistName, String albumName, List<String> tracksList);
}
