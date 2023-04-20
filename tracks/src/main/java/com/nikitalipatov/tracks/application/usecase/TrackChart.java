package com.nikitalipatov.tracks.application.usecase;

import com.nikitalipatov.tracks.application.model.TrackModel;

import java.util.List;

public interface TrackChart {

    List<TrackModel> createTracksChart(String sortParameter);
}
