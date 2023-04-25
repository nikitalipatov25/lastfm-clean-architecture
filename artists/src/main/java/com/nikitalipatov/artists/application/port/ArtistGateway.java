package com.nikitalipatov.artists.application.port;

import com.nikitalipatov.artists.application.model.ArtistModel;

import java.util.List;

public interface ArtistGateway {

    ArtistModel save(ArtistModel artistModel);

    ArtistModel getArtistInfo(String artistId);

    List<ArtistModel> getArtistsChartByListeners();

    List<ArtistModel> getArtistsChartByPlayCount();

    boolean isArtistExist(String artistId);

    void sendIfError(String albumId);

}
