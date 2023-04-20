package com.nikitalipatov.tracks.application.usecase.impl;

import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.application.port.TrackGateway;
import com.nikitalipatov.tracks.application.usecase.FindTrack;
import com.nikitalipatov.tracks.application.usecase.SaveTrack;
import com.nikitalipatov.tracks.domain.entity.LocalTrack;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindTrackImpl implements FindTrack {

    private final static String KEY = "1f6a20f083019ed436ee46dce7c3400c";
    private final TrackGateway trackGateway;
    private final SaveTrack saveTrack;

    @Override
    public TrackModel findTrack(String artistName, String trackName) {
        Caller.getInstance().setUserAgent("tst");
        // найти трек локально, если нет - найти в апи, сохранить трек, сказать в очередь, что нужно сохранить артиста и альбом,
        // вернуть трек ( если альбом или исполнитель не сохранятся - удалить трек локально)
        try {
            return trackGateway.getTrackInfo(artistName, trackName);
        } catch (Exception e) {
            var trackFromApi = Track.getInfo(artistName, trackName, KEY);
            var tags = new ArrayList<>(trackFromApi.getTags());
            var track = LocalTrack.of(trackFromApi.getMbid(), trackFromApi.getName(), trackFromApi.getDuration(),
                    trackFromApi.getPlaycount(), trackFromApi.getListeners(), trackFromApi.getArtist(), trackFromApi.getArtistMbid(),
                    trackFromApi.getAlbum(), trackFromApi.getAlbumMbid(), tags);
            return saveTrack.save(track);
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
