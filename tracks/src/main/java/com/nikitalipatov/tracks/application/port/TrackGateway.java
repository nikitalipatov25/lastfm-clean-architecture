package com.nikitalipatov.tracks.application.port;

import com.nikitalipatov.tracks.application.model.TrackModel;

public interface TrackGateway {

    TrackModel save(TrackModel trackModel);

    TrackModel getTrackInfo(String artistName, String trackName);

}
