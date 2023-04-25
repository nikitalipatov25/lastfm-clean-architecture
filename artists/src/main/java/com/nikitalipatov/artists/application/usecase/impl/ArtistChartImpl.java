package com.nikitalipatov.artists.application.usecase.impl;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.application.port.ArtistGateway;
import com.nikitalipatov.artists.application.usecase.ArtistChart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nikitalipatov.common.constant.Constants.LISTENERS;
import static com.nikitalipatov.common.constant.Constants.PLAY_COUNT;

@Service
@RequiredArgsConstructor
public class ArtistChartImpl implements ArtistChart {

    private final ArtistGateway artistGateway;

    @Override
    public List<ArtistModel> createArtistsChart(String sortParameter) {
        return switch (sortParameter) {
            case LISTENERS -> artistGateway.getArtistsChartByListeners();
            case PLAY_COUNT -> artistGateway.getArtistsChartByPlayCount();
            default -> throw new IllegalArgumentException("Unexpected value " + sortParameter);
        };
    }
}
