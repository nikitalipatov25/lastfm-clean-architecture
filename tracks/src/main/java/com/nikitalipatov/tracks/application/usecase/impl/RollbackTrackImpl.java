package com.nikitalipatov.tracks.application.usecase.impl;

import com.nikitalipatov.tracks.application.port.TrackGateway;
import com.nikitalipatov.tracks.application.usecase.RollbackTrack;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RollbackTrackImpl implements RollbackTrack {

    private final TrackGateway trackGateway;

    @Override
    public void rollback(String artistName, String albumName) {
//        if (trackGateway.isTrackExists())
        trackGateway.deleteTrack(artistName, albumName);
    }
}
