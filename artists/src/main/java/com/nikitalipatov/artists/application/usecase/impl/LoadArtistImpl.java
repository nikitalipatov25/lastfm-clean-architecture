package com.nikitalipatov.artists.application.usecase.impl;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.application.port.ArtistGateway;
import com.nikitalipatov.artists.application.usecase.LoadArtist;
import com.nikitalipatov.artists.domain.entity.LocalArtist;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nikitalipatov.common.constant.Constants.API_KEY;
import static com.nikitalipatov.common.constant.Constants.USER_AGENT;

@Service
@RequiredArgsConstructor
public class LoadArtistImpl implements LoadArtist {

    private final ArtistGateway artistGateway;

    @Override
    @Transactional
    public void loadArtist(String artistName, String albumId) {
        try {
            Caller.getInstance().setUserAgent(USER_AGENT);
            var artistFromApi = Artist.getInfo(artistName, API_KEY);
            if (!artistGateway.isArtistExist(artistFromApi.getMbid())) {
                var artist = LocalArtist.of(artistFromApi.getMbid(), artistFromApi.getName(), artistFromApi.getPlaycount(),
                        artistFromApi.getListeners(), (List<String>) artistFromApi.getTags());
                artistGateway.save(ArtistModel.builder()
                        .id(artist.getId())
                        .name(artist.getName())
                        .listeners(artist.getListeners())
                        .playCount(artist.getPlayCount())
                        .build());
            }
        } catch (Exception e) {
            artistGateway.sendIfError(albumId);
        }
    }
}
