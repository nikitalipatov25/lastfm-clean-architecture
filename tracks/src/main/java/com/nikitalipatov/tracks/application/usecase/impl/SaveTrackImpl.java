package com.nikitalipatov.tracks.application.usecase.impl;

import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.application.port.TrackGateway;
import com.nikitalipatov.tracks.application.usecase.SaveTrack;
import com.nikitalipatov.tracks.domain.entity.LocalTrack;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveTrackImpl implements SaveTrack {

    private final TrackGateway trackGateway;

    @Override
    public TrackModel save(LocalTrack localTrack) {
        return trackGateway.save(TrackModel.builder()
                .id(localTrack.getId())
                .name(localTrack.getName())
                .duration(localTrack.getDuration())
                .listeners(localTrack.getListeners())
                .playCount(localTrack.getPlayCount())
                .album(localTrack.getAlbum())
                .albumId(localTrack.getAlbumId())
                .artist(localTrack.getArtist())
                .artistId(localTrack.getArtistId())
                .tagsList(localTrack.getTagsList())
                .build());
    }
}
