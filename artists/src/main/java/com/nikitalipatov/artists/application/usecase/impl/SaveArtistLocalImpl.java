package com.nikitalipatov.artists.application.usecase.impl;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.application.port.ArtistGateway;
import com.nikitalipatov.artists.application.usecase.SaveArtistLocal;
import com.nikitalipatov.artists.domain.entity.LocalArtist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveArtistLocalImpl implements SaveArtistLocal {

    private final ArtistGateway artistGateway;

    @Override
    public ArtistModel saveArtistFromLastFm(LocalArtist localArtist) {
        return artistGateway.save(ArtistModel.builder()
                .id(localArtist.getId())
                .name(localArtist.getName())
                .listeners(localArtist.getListeners())
                .playCount(localArtist.getPlayCount())
                .build());
    }
}
