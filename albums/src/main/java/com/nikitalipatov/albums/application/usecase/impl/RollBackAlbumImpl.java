package com.nikitalipatov.albums.application.usecase.impl;

import com.nikitalipatov.albums.application.port.AlbumGateway;
import com.nikitalipatov.albums.application.usecase.RollbackAlbum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RollBackAlbumImpl implements RollbackAlbum {

    private final AlbumGateway albumGateway;

    @Override
    public void rollback(String albumId) {
        albumGateway.rollback(albumId);
    }
}
