package com.nikitalipatov.artists.application.usecase;

import com.nikitalipatov.artists.application.model.ArtistModel;

import java.util.List;

public interface ArtistChart {

    List<ArtistModel> createArtistsChart(String sortParameter);
}
