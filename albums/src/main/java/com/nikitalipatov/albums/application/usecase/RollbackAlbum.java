package com.nikitalipatov.albums.application.usecase;

public interface RollbackAlbum {

    void rollback(String albumId);
}
