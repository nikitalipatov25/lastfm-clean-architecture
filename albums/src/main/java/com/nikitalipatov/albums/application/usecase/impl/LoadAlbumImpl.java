package com.nikitalipatov.albums.application.usecase.impl;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.application.port.AlbumGateway;
import com.nikitalipatov.albums.application.usecase.LoadAlbum;
import com.nikitalipatov.albums.domain.entity.LocalAlbum;
import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.common.dto.OrchestratorDto;
import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import de.umass.lastfm.MusicEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.nikitalipatov.common.constant.Constants.API_KEY;
import static com.nikitalipatov.common.enums.Status.FAIL;
import static com.nikitalipatov.common.enums.Status.SUCCESS;

@Service
@RequiredArgsConstructor
public class LoadAlbumImpl implements LoadAlbum {

    private final AlbumGateway albumGateway;

    @Override
    public OrchestratorDto loadAlbum(String artistName, String albumName) {
        /*
         * 1 Проверить сохранен ли альбом локально
         * Если да - просто возвращаем альбом
         * Если нет - шаг 2
         * 2 Запросить альбом с апи
         * 3 вернуть альбом
         * 4 сохранить альбом
         * с помощью брокера сообщить в сервис треков, что альбом сохранен и надо сохранить все треки
         * если треки не сохранились - удаляем альбом
         * шаг 4 должен быть асинхронным
         */
        OrchestratorDto result = new OrchestratorDto();
        try {
            Caller.getInstance().setUserAgent("tst");
            if (!albumGateway.isAlbumExists(artistName, albumName)) {
                var albumFromApi = Album.getInfo(artistName, albumName, API_KEY);
                var artistId = Artist.getInfo(albumFromApi.getArtist(), API_KEY).getMbid();
                var album = LocalAlbum.of(albumFromApi.getMbid(), albumFromApi.getName(), artistId,
                        albumFromApi.getPlaycount(), albumFromApi.getListeners());
                albumGateway.save(AlbumModel.builder()
                        .id(album.getId())
                        .name(album.getName())
                        .artistId(album.getArtistId())
                        .listeners(album.getListeners())
                        .playCount(album.getPlayCount())
                        .build());
                result.setAlbumStatus(SUCCESS.name());
            }
        } catch (Exception e) {
            result.setAlbumStatus(FAIL.name());
        }
        return result;
    }
}
