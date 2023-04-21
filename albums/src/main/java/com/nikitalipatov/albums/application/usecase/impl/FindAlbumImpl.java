package com.nikitalipatov.albums.application.usecase.impl;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.application.port.AlbumGateway;
import com.nikitalipatov.albums.application.usecase.FindAlbum;
import com.nikitalipatov.albums.application.usecase.SaveAlbum;
import com.nikitalipatov.albums.domain.entity.LocalAlbum;
import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import de.umass.lastfm.MusicEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.nikitalipatov.common.constant.Constants.API_KEY;

@Service
@RequiredArgsConstructor
public class FindAlbumImpl implements FindAlbum {

    private final static String KEY = "1f6a20f083019ed436ee46dce7c3400c";
    private final AlbumGateway albumGateway;

    @Override
    public AlbumModel findAlbum(String albumName, String artistName) {
        /*
         * 1 Проверить сохранен ли альбом локально
         * Если да - просто возвращаем альбом
         * Если нет - шаг 2
         * 2 Запросить альбом с апи
         * 3 вернуть альбом
         * 4 сохранить альбом
         * с помощью брокера сообщить в сервис треков, что альбом сохранен и надо сохранить все треки и артистам тоже
         * если треки не сохранились - удаляем альбом
         * шаг 4 должен быть асинхронным
         */
        Caller.getInstance().setUserAgent("tst");
        try {
            return albumGateway.getAlbumInfo(albumName, artistName);
        } catch (Exception e) {
            var albumFromApi = Album.getInfo(albumName, artistName, KEY);
            var artistId = Artist.getInfo(albumFromApi.getArtist(), API_KEY).getMbid();
            // альбом найден, надо сохранить треки и артиста?
            albumGateway.sendInfo(albumFromApi.getArtist(), albumFromApi.getName());
            var album = LocalAlbum.of(albumFromApi.getMbid(), albumFromApi.getName(), artistId,
                    albumFromApi.getPlaycount(), albumFromApi.getListeners());
            return albumGateway.save(AlbumModel.builder()
                    .id(album.getId())
                    .name(album.getName())
                    .artistId(album.getArtistId())
                    .listeners(album.getListeners())
                    .playCount(album.getPlayCount())
                    .build());
        }
    }
}
