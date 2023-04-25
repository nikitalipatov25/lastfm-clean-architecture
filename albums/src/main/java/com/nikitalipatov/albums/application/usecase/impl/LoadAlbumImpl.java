package com.nikitalipatov.albums.application.usecase.impl;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.application.port.AlbumGateway;
import com.nikitalipatov.albums.application.usecase.LoadAlbum;
import com.nikitalipatov.albums.domain.entity.LocalAlbum;
import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.nikitalipatov.common.constant.Constants.API_KEY;
import static com.nikitalipatov.common.constant.Constants.USER_AGENT;

@Service
@RequiredArgsConstructor
public class LoadAlbumImpl implements LoadAlbum {

    private final AlbumGateway albumGateway;

    @Override
    public void loadAlbum(String artistName, String albumName, String albumId) {
        try {
            Caller.getInstance().setUserAgent(USER_AGENT);
            var artistFromApi = Artist.getInfo(artistName, API_KEY);
            var albumFromApi = Album.getInfo(artistName, albumName, API_KEY);
            if (!albumGateway.isAlbumExists(albumId)) {
                var album = LocalAlbum.of(albumId, albumFromApi.getName(), artistFromApi.getMbid(),
                        albumFromApi.getPlaycount(), albumFromApi.getListeners());
                albumGateway.save(AlbumModel.builder()
                        .id(album.getId())
                        .name(album.getName())
                        .artistId(album.getArtistId())
                        .listeners(album.getListeners())
                        .playCount(album.getPlayCount())
                        .build());
            }
            albumGateway.sendToArtist(artistFromApi.getName(), albumId);
        } catch (Exception e) {
            albumGateway.sendIfError(albumId);
        }
    }
}
