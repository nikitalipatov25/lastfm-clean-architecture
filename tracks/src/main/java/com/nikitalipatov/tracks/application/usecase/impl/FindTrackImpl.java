package com.nikitalipatov.tracks.application.usecase.impl;

import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.application.port.TrackGateway;
import com.nikitalipatov.tracks.application.usecase.FindTrack;
import com.nikitalipatov.tracks.application.usecase.LoadTrack;
import com.nikitalipatov.tracks.domain.entity.LocalTrack;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.nikitalipatov.common.constant.Constants.API_KEY;
import static com.nikitalipatov.common.constant.Constants.USER_AGENT;

@Service
@RequiredArgsConstructor
public class FindTrackImpl implements FindTrack {

    private final TrackGateway trackGateway;
    private final LoadTrack loadTrack;

    @Override
    @Transactional
    public TrackModel findTrack(String artistName, String trackName) {
        Caller.getInstance().setUserAgent(USER_AGENT);
        var trackFromApi = Track.getInfo(artistName, trackName, API_KEY);
        if (trackGateway.isTrackExists(trackFromApi.getMbid())) {
            return findTrackLocally(trackFromApi.getAlbumMbid());
        } else {
            var tags = new ArrayList<>(trackFromApi.getTags());
            var track = LocalTrack.of(trackFromApi.getMbid(), trackFromApi.getName(),
                    trackFromApi.getPlaycount(), trackFromApi.getListeners(), trackFromApi.getArtistMbid(),
                    trackFromApi.getAlbumMbid(), tags);
            CompletableFuture.runAsync(() -> loadTrack.loadTrack(trackFromApi.getArtist(), trackFromApi.getName()));
            return TrackModel.builder()
                    .id(track.getId())
                    .name(track.getName())
                    .listeners(track.getListeners())
                    .playCount(track.getPlayCount())
                    .albumId(track.getAlbumId())
                    .artistId(track.getArtistId())
                    .tagsList(track.getTagsList())
                    .build();
        }
    }

    @Override
    public List<TrackModel> findTrack(String artistName) {
        Caller.getInstance().setUserAgent(USER_AGENT);
        var artist = Artist.getInfo(artistName, API_KEY);
        return trackGateway.getTracksByArtist(artist.getMbid());
    }

    @Override
    public List<TrackModel> findTrack(List<String> genreList) {
        return trackGateway.getTracksByGenre(genreList);
    }

    private TrackModel findTrackLocally(String trackId) {
        return trackGateway.getTrackInfo(trackId);
    }
}
