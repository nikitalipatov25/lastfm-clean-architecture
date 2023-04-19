package com.nikitalipatov.artists.application.usecase;

import com.nikitalipatov.artists.application.model.ArtistModel;

import java.util.List;

public interface CreateChartOfArtists {

    List<ArtistModel> createChartOfLocalArtist(String sortParameter);
}
