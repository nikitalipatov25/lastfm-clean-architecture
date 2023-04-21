package com.nikitalipatov.artists.application.usecase;

import com.nikitalipatov.artists.application.model.ArtistModel;

public interface FindArtist {

    ArtistModel findArtist(String artistName);
}
