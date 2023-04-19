package com.nikitalipatov.artists.application.usecase;

import com.nikitalipatov.artists.application.model.ArtistModel;

public interface RequestArtist {

    ArtistModel getArtistFomLastFm(String artistName);
}
