package com.nikitalipatov.albums.application.port;

import com.nikitalipatov.albums.application.model.AlbumModel;

import java.util.List;

public interface AlbumGateway {

    AlbumModel save(AlbumModel albumModel);

    AlbumModel getAlbumInfo(String artistName, String albumName);

    boolean isAlbumExists(String artistName, String albumName);

    void delete(String artistName, String albumName);

    void sendInfo(String artistName, String albumName);
}
