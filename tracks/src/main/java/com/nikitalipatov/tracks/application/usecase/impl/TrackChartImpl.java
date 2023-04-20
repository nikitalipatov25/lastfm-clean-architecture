package com.nikitalipatov.tracks.application.usecase.impl;

import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.application.port.TrackGateway;
import com.nikitalipatov.tracks.application.usecase.TrackChart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackChartImpl implements TrackChart {

    private static final String LISTENERS = "listeners";
    private static final String PLAY_COUNT = "playCount";
    private final TrackGateway trackGateway;

    @Override
    public List<TrackModel> createTracksChart(String sortParameter) {
        return switch (sortParameter) {
            case LISTENERS -> trackGateway.getTrackByListeners();
            case PLAY_COUNT -> trackGateway.getTrackByPlayCount();
            default -> throw new IllegalArgumentException("Unexpected value " + sortParameter);
        };
    }
}
