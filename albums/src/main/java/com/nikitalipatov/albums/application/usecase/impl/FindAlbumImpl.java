package com.nikitalipatov.albums.application.usecase.impl;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.application.port.AlbumGateway;
import com.nikitalipatov.albums.application.usecase.FindAlbum;
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
public class FindAlbumImpl implements FindAlbum {

    private final AlbumGateway albumGateway;

    @Override
    public AlbumModel findAlbum(String albumName, String artistName) {
        Caller.getInstance().setUserAgent(USER_AGENT);
        var albumFromApi = Album.getInfo(albumName, artistName, API_KEY);
        if (albumGateway.isAlbumExists(albumFromApi.getMbid())) {
            return findAlbumLocally(albumFromApi.getMbid());
        }
        var artistId = Artist.getInfo(albumFromApi.getArtist(), API_KEY).getMbid();
        var album = LocalAlbum.of(albumFromApi.getMbid(), albumFromApi.getName(), artistId,
                albumFromApi.getPlaycount(), albumFromApi.getListeners());
        albumGateway.loadTracks(artistId, albumFromApi.getName());
        return AlbumModel.builder()
                .id(album.getId())
                .name(album.getName())
                .artistId(album.getArtistId())
                .listeners(album.getListeners())
                .playCount(album.getPlayCount())
                .build();
        }

    private AlbumModel findAlbumLocally(String albumId) {
        return albumGateway.findAlbum(albumId);
    }
}
