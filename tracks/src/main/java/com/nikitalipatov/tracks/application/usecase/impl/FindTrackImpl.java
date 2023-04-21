package com.nikitalipatov.tracks.application.usecase.impl;

import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.application.port.TrackGateway;
import com.nikitalipatov.tracks.application.usecase.FindTrack;
import com.nikitalipatov.tracks.domain.entity.LocalTrack;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.nikitalipatov.common.constant.Constants.API_KEY;

@Service
@RequiredArgsConstructor
public class FindTrackImpl implements FindTrack {

    private final TrackGateway trackGateway;

    @Override
    @Transactional
    public TrackModel findTrack(String artistName, String trackName) {
        Caller.getInstance().setUserAgent("tst");
        if (trackGateway.isTrackExists(artistName, trackName)) {
            return trackGateway.getTrackInfo(artistName, trackName);
        } else {
            var trackFromApi = Track.getInfo(artistName, trackName, API_KEY);
            var tags = new ArrayList<>(trackFromApi.getTags());
            var track = LocalTrack.of(trackFromApi.getMbid(), trackFromApi.getName(),
                    trackFromApi.getPlaycount(), trackFromApi.getListeners(), trackFromApi.getArtistMbid(),
                    trackFromApi.getAlbumMbid(), tags);
            var result = trackGateway.save((TrackModel.builder()
                    .id(track.getId())
                    .name(track.getName())
                    .listeners(track.getListeners())
                    .playCount(track.getPlayCount())
                    .albumId(track.getAlbumId())
                    .artistId(track.getArtistId())
                    .tagsList(track.getTagsList())
                    .build()));
            trackGateway.sendInfo(KafkaMessage.builder()
                    .eventType(EventType.TRACK_SAVE)
                    .status(Status.SUCCESS)
                    // TODO: 21.04.2023 artist name заменить на ид и с альбомом тоже самое
                    .artistId(track.getArtistId())
                    .albumId(track.getAlbumId())
                    .build());
            return result;
        }
    }

    @Override
    public List<TrackModel> findTrack(String artistName) {
        return trackGateway.getTracksByArtist(artistName);
    }

    @Override
    public List<TrackModel> findTrack(List<String> genreList) {
        return trackGateway.getTracksByGenre(genreList);
    }
}
