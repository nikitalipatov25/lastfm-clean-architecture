package com.nikitalipatov.artists.application.port;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.common.dto.KafkaMessage;

import java.util.List;

public interface ArtistGateway {

    ArtistModel save(ArtistModel artistModel);

    ArtistModel getArtistInfo(String artistName);

    List<ArtistModel> getArtistsChartByListeners();

    List<ArtistModel> getArtistsChartByPlayCount();

    boolean isArtistExist(String artistName);

    void sendInfo(KafkaMessage kafkaMessage);
}
