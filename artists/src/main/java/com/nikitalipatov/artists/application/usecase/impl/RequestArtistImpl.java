package com.nikitalipatov.artists.application.usecase.impl;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.application.port.ArtistGateway;
import com.nikitalipatov.artists.application.usecase.RequestArtist;
import com.nikitalipatov.artists.application.usecase.SaveArtistLocal;
import com.nikitalipatov.artists.domain.entity.LocalArtist;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestArtistImpl implements RequestArtist {

    private final static String KEY = "1f6a20f083019ed436ee46dce7c3400c";
    private final ArtistGateway artistGateway;
    private final SaveArtistLocal saveArtistLocal;

    @Override
    public ArtistModel getArtistFomLastFm(String artistName) {
        Caller.getInstance().setUserAgent("tst");
        // пытаемся найти артиста в базе, если нет - обращаемся к апи и если находим - сохраняем локально, а есди не находим - то??? и если при сохранении что-то пойдет не так???
        try {
            return artistGateway.getArtistInfo(artistName);
        } catch (Exception e) {
            var artistFromApi = Artist.getInfo(artistName, KEY);
            // TODO: 19.04.2023 тут будет проверка на null, чтобы не создать пустого артиста
            var artist =
                    LocalArtist.of(artistFromApi.getMbid(), artistFromApi.getName(), artistFromApi.getPlaycount(), artistFromApi.getListeners(), (List<String>) artistFromApi.getTags());
            return saveArtistLocal.saveArtistFromLastFm(artist);
        }
    }
}
