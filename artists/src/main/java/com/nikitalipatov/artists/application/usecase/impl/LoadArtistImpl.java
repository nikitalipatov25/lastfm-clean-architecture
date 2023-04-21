package com.nikitalipatov.artists.application.usecase.impl;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.application.port.ArtistGateway;
import com.nikitalipatov.artists.application.usecase.LoadArtist;
import com.nikitalipatov.artists.domain.entity.LocalArtist;
import com.nikitalipatov.common.dto.OrchestratorDto;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nikitalipatov.common.constant.Constants.API_KEY;
import static com.nikitalipatov.common.enums.Status.FAIL;
import static com.nikitalipatov.common.enums.Status.SUCCESS;

@Service
@RequiredArgsConstructor
public class LoadArtistImpl implements LoadArtist {

    private final ArtistGateway artistGateway;

    @Override
    @Transactional
    public OrchestratorDto loadArtist(String artistName) {
        OrchestratorDto result = new OrchestratorDto();
        try {
            Caller.getInstance().setUserAgent("tst");
            if (!artistGateway.isArtistExist(artistName)) {
                var artistFromApi = Artist.getInfo(artistName, API_KEY);
                // TODO: 19.04.2023 тут будет проверка на null, чтобы не создать пустого артиста
                var artist =
                        LocalArtist.of(artistFromApi.getMbid(), artistFromApi.getName(), artistFromApi.getPlaycount(),
                                artistFromApi.getListeners(), (List<String>) artistFromApi.getTags());
                artistGateway.save(ArtistModel.builder()
                        .id(artist.getId())
                        .name(artist.getName())
                        .listeners(artist.getListeners())
                        .playCount(artist.getPlayCount())
                        .build());
                result.setArtistStatus(SUCCESS.name());
                // ВОЗМОЖНО НЕ НУЖНО
//                artistGateway.sendInfo(KafkaMessage.builder()
//                        .eventType(ARTIST_SAVE)
//                        .status(SUCCESS)
//                        .artistName(artistName)
//                        .build());
            }
        } catch (Exception e) {
            result.setArtistStatus(FAIL.name());
//            artistGateway.sendInfo(KafkaMessage.builder()
//                    .eventType(ARTIST_SAVE)
//                    .status(FAIL)
//                    .artistName(artistName)
//                    .build());
        }
        return result;
    }
}
