package com.nikitalipatov.tracks.application.usecase.impl;

import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.common.dto.OrchestratorDto;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.application.port.TrackGateway;
import com.nikitalipatov.tracks.application.usecase.LoadTrack;
import com.nikitalipatov.tracks.domain.entity.LocalTrack;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.nikitalipatov.common.constant.Constants.API_KEY;
import static com.nikitalipatov.common.enums.Status.FAIL;
import static com.nikitalipatov.common.enums.Status.SUCCESS;

@Service
@RequiredArgsConstructor
public class LoadTrackImpl implements LoadTrack {

    private final TrackGateway trackGateway;

    @Override
    @Transactional
    public OrchestratorDto loadTrack(String artistName, String albumName, List<String> tracksList) {
        OrchestratorDto result = new OrchestratorDto();
        try {
            Caller.getInstance().setUserAgent("tst");
            tracksList.forEach(track -> CompletableFuture.runAsync(() -> {
                if (!trackGateway.isTrackExists(track, albumName)) {
                    var trackFromApi = Track.getInfo(artistName, track, API_KEY);
                    var tags = new ArrayList<>(trackFromApi.getTags());
                    var loadTrack = LocalTrack.of(trackFromApi.getMbid(), trackFromApi.getName(),
                            trackFromApi.getPlaycount(), trackFromApi.getListeners(),  trackFromApi.getArtistMbid(),
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
//                trackGateway.sendInfo(KafkaMessage.builder()
//                        .eventType(EventType.TRACK_SAVE)
//                        .status(SUCCESS)
//                        .artistName(artistName)
//                        .albumName(albumName)
//                        .build());
            }));
            result.setTrackStatus(SUCCESS.name());
        } catch (Exception e) {
            result.setTrackStatus(FAIL.name());
//            trackGateway.sendInfo(KafkaMessage.builder()
//                    .eventType(EventType.TRACK_SAVE)
//                    .status(Status.FAIL)
//                    .artistName(artistName)
//                    .albumName(albumName)
//                    .build());
        }
        return result;
    }
}
