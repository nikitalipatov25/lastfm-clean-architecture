package com.nikitalipatov.artists.application.usecase;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.domain.entity.LocalArtist;

public interface SaveArtistLocal {

    ArtistModel saveArtistFromLastFm(LocalArtist localArtist);
}
