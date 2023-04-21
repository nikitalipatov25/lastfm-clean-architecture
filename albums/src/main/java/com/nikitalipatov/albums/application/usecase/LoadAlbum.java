package com.nikitalipatov.albums.application.usecase;

import com.nikitalipatov.common.dto.OrchestratorDto;

public interface LoadAlbum {
    OrchestratorDto loadAlbum(String artistName, String albumName);
}
