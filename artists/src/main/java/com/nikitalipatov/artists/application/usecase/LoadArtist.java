package com.nikitalipatov.artists.application.usecase;

import com.nikitalipatov.common.dto.OrchestratorDto;

public interface LoadArtist {

    OrchestratorDto loadArtist(String artistName);
}
