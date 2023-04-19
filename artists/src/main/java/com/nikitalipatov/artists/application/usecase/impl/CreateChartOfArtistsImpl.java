package com.nikitalipatov.artists.application.usecase.impl;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.application.port.ArtistGateway;
import com.nikitalipatov.artists.application.usecase.CreateChartOfArtists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateChartOfArtistsImpl implements CreateChartOfArtists {

    private static final String LISTENERS = "listeners";
    private static final String PLAY_COUNT = "playCount";
    private final ArtistGateway artistGateway;

    @Override
    public List<ArtistModel> createChartOfLocalArtist(String sortParameter) {
        return switch (sortParameter) {
            case LISTENERS -> artistGateway.getArtistsChartByListeners();
            case PLAY_COUNT -> artistGateway.getArtistsChartByPlayCount();
            default -> throw new IllegalArgumentException("Unexpected value " + sortParameter);
        };
    }
}
