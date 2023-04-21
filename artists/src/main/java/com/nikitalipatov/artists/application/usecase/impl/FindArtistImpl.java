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

@Service
@RequiredArgsConstructor
public class FindArtistImpl implements FindArtist {

    private final ArtistGateway artistGateway;

    @Override
    public ArtistModel findArtist(String artistName) {
        Caller.getInstance().setUserAgent("tst");
        if (artistGateway.isArtistExist(artistName)) {
            return artistGateway.getArtistInfo(artistName);
        } else {
            var artistFromApi = Artist.getInfo(artistName, API_KEY);
            // TODO: 19.04.2023 тут будет проверка на null, чтобы не создать пустого артиста
            var artist =
                    LocalArtist.of(artistFromApi.getMbid(), artistFromApi.getName(), artistFromApi.getPlaycount(),
                            artistFromApi.getListeners(), (List<String>) artistFromApi.getTags());
            return artistGateway.save(ArtistModel.builder()
                    .id(artist.getId())
                    .name(artist.getName())
                    .listeners(artist.getListeners())
                    .playCount(artist.getPlayCount())
                    .build());
        }
        // пытаемся найти артиста в базе, если нет - обращаемся к апи и если находим - сохраняем локально, а есди не находим - то??? и если при сохранении что-то пойдет не так???
    }
}
