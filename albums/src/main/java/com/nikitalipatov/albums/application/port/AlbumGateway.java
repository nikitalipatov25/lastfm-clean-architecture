package com.nikitalipatov.albums.application.port;

import com.nikitalipatov.albums.application.model.AlbumModel;

public interface AlbumGateway {

    AlbumModel save(AlbumModel albumModel);

    AlbumModel findAlbum(String albumId);

    boolean isAlbumExists(String albumId);

    void rollback(String albumId);

    void sendIfError(String albumId);

    void sendToArtist(String artistName, String albumId);

    void loadTracks(String artistName, String albumName);
}
