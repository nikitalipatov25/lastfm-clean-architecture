package com.nikitalipatov.albums.application.usecase;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.domain.entity.LocalAlbum;

public interface SaveAlbumLocal {

    AlbumModel save(LocalAlbum localAlbum);
}
