package com.nikitalipatov.albums.application.usecase.impl;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.application.port.AlbumGateway;
import com.nikitalipatov.albums.application.usecase.SaveAlbumLocal;
import com.nikitalipatov.albums.domain.entity.LocalAlbum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveAlbumLocalImpl implements SaveAlbumLocal {

    private final AlbumGateway albumGateway;

    @Override
    public AlbumModel save(LocalAlbum localAlbum) {
        return albumGateway.save(AlbumModel.builder()
                .id(localAlbum.getId())
                .name(localAlbum.getName())
                .artist(localAlbum.getArtist())
                .playCount(localAlbum.getPlayCount())
                .listeners(localAlbum.getListeners())
                .tags(localAlbum.getTagsList())
                .trackIdsList(localAlbum.getTrackNames())
                .build());
    }
}
