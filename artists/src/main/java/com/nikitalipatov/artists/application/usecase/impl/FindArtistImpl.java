package com.nikitalipatov.artists.application.usecase.impl;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.application.port.ArtistGateway;
import com.nikitalipatov.artists.application.usecase.FindArtist;
import com.nikitalipatov.artists.domain.entity.LocalArtist;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nikitalipatov.common.constant.Constants.API_KEY;
import static com.nikitalipatov.common.constant.Constants.USER_AGENT;

@Service
@RequiredArgsConstructor
public class FindArtistImpl implements FindArtist {

    private final ArtistGateway artistGateway;

    @Override
    public ArtistModel findArtist(String artistName) {
        Caller.getInstance().setUserAgent(USER_AGENT);
        var artistFromApi = Artist.getInfo(artistName, API_KEY);
        if (artistGateway.isArtistExist(artistFromApi.getMbid())) {
            return findArtistLocally(artistFromApi.getMbid());
        } else {
            var artist = LocalArtist.of(artistFromApi.getMbid(), artistFromApi.getName(), artistFromApi.getPlaycount(),
                    artistFromApi.getListeners(), (List<String>) artistFromApi.getTags());
            return ArtistModel.builder()
                    .id(artist.getId())
                    .name(artist.getName())
                    .listeners(artist.getListeners())
                    .playCount(artist.getPlayCount())
                    .build();
        }
    }

    public ArtistModel findArtistLocally(String artistId) {
        return artistGateway.getArtistInfo(artistId);
    }
}
