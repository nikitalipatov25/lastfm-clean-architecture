package com.nikitalipatov.albums.application.usecase;

import com.nikitalipatov.albums.application.model.AlbumModel;

public interface LoadAlbumFromLastFm {

    AlbumModel getAlbumWithTracksFromLastFm(String albumName, String artistName);
}
