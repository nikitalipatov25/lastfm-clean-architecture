package com.nikitalipatov.albums.application.usecase.impl;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.application.port.AlbumGateway;
import com.nikitalipatov.albums.application.usecase.LoadAlbumFromLastFm;
import com.nikitalipatov.albums.application.usecase.SaveAlbumLocal;
import com.nikitalipatov.albums.domain.entity.LocalAlbum;
import de.umass.lastfm.Album;
import de.umass.lastfm.Caller;
import de.umass.lastfm.MusicEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoadAlbumFromLastFmImpl implements LoadAlbumFromLastFm {

    private final static String KEY = "1f6a20f083019ed436ee46dce7c3400c";
    private final SaveAlbumLocal saveAlbumLocal;
    private final AlbumGateway albumGateway;

    @Override
    public AlbumModel getAlbumWithTracksFromLastFm(String albumName, String artistName) {
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
        Caller.getInstance().setUserAgent("tst");
        try {
            return albumGateway.getAlbumInfo(albumName, artistName);
        } catch (Exception e) {
            var albumFromApi = Album.getInfo("Linkin Park", albumName, KEY);
            var tracks = albumFromApi.getTracks().stream().map(MusicEntry::getName).collect(Collectors.toList());
            var tags = new ArrayList<>(albumFromApi.getTags());
            /*
             * Тут параллельно надо сохранять треки
             * для этого воспользоваться очередью и передать название треков в другой сервис
             */
            var album = LocalAlbum.of(albumFromApi.getMbid(), albumFromApi.getName(), albumFromApi.getArtist(),
                    albumFromApi.getPlaycount(), albumFromApi.getListeners(), tags, tracks);
            return saveAlbumLocal.save(album);
        }
    }
}
