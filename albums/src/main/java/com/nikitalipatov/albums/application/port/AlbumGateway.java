package com.nikitalipatov.albums.application.port;

import com.nikitalipatov.albums.application.model.AlbumModel;

public interface AlbumGateway {

    AlbumModel save(AlbumModel albumModel);

    AlbumModel getAlbumInfo(String artistName, String albumName);
}
