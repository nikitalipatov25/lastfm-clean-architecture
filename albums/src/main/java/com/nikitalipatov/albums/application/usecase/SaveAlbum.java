package com.nikitalipatov.albums.application.usecase;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.domain.entity.LocalAlbum;

public interface SaveAlbum {

    AlbumModel save(LocalAlbum localAlbum);
}
