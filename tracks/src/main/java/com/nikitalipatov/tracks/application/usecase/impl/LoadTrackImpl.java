package com.nikitalipatov.tracks.application.usecase.impl;

import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.application.port.TrackGateway;
import com.nikitalipatov.tracks.application.usecase.LoadTrack;
import com.nikitalipatov.tracks.domain.entity.LocalTrack;
import de.umass.lastfm.Album;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static com.nikitalipatov.common.constant.Constants.API_KEY;
import static com.nikitalipatov.common.constant.Constants.USER_AGENT;

@Service
@RequiredArgsConstructor
public class LoadTrackImpl implements LoadTrack {

    private final TrackGateway trackGateway;

    @Override
    @Transactional
    public void loadTrack(String artistName, String trackName) {
        Caller.getInstance().setUserAgent(USER_AGENT);
            var trackFromApi = Track.getInfo(artistName, trackName, API_KEY);
            if (!trackGateway.isTrackExists(trackFromApi.getMbid())) {
                var tags = new ArrayList<>(trackFromApi.getTags());
                var loadTrack = LocalTrack.of(trackFromApi.getMbid(), trackFromApi.getName(),
                        trackFromApi.getPlaycount(), trackFromApi.getListeners(), trackFromApi.getArtistMbid(),
                        trackFromApi.getAlbumMbid(), tags);
                trackGateway.save((TrackModel.builder()
                        .id(loadTrack.getId())
                        .name(loadTrack.getName())
                        .listeners(loadTrack.getListeners())
                        .playCount(loadTrack.getPlayCount())
                        .albumId(loadTrack.getAlbumId())
                        .artistId(loadTrack.getArtistId())
                        .tagsList(loadTrack.getTagsList())
                        .build()));
            }
            trackGateway.sendToAlbum(trackFromApi.getArtist(), trackFromApi.getAlbum(), trackFromApi.getAlbumMbid());
    }

    @Override
    @Transactional
    public void loadAllTracks(String artistName, String albumName) {
        Caller.getInstance().setUserAgent(USER_AGENT);
        var albumFromApi = Album.getInfo(artistName, albumName, API_KEY);
        var tracks = albumFromApi.getTracks();
        tracks.forEach(track -> {
            if (!trackGateway.isTrackExists(track.getMbid())) {
                var loadTrack = LocalTrack.of(track.getMbid(), track.getName(),
                        track.getPlaycount(), track.getListeners(),  track.getArtistMbid(),
                        track.getAlbumMbid(), track.getTags().stream().toList());
                trackGateway.save((TrackModel.builder()
                        .id(loadTrack.getId())
                        .name(loadTrack.getName())
                        .listeners(loadTrack.getListeners())
                        .playCount(loadTrack.getPlayCount())
                        .albumId(loadTrack.getAlbumId())
                        .artistId(loadTrack.getArtistId())
                        .tagsList(loadTrack.getTagsList())
                        .build()));
            }
        });
        trackGateway.sendToAlbum(artistName, albumName, albumFromApi.getMbid());
    }
}
