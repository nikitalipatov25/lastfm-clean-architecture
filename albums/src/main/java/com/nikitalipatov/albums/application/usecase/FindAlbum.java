package com.nikitalipatov.albums.application.usecase;

import com.nikitalipatov.albums.application.model.AlbumModel;

public interface FindAlbum {

    AlbumModel findAlbum(String artistName, String albumName);
}
