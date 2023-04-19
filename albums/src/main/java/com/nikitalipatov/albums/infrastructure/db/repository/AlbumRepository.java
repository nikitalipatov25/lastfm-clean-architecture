package com.nikitalipatov.albums.infrastructure.db.repository;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.application.port.AlbumGateway;
import com.nikitalipatov.albums.infrastructure.db.dao.AlbumDao;
import com.nikitalipatov.albums.infrastructure.db.mapper.AlbumMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlbumRepository implements AlbumGateway {

    private final AlbumDao albumDao;
    private final AlbumMapper albumMapper;

    @Override
    public AlbumModel save(AlbumModel albumModel) {
        var album = albumMapper.toAlbumEntity(albumModel);
        return albumMapper.toModel(albumDao.save(album));
    }

    // TODO: 19.04.2023 custom exception
    @Override
    public AlbumModel getAlbumInfo(String artistName, String albumName) {
        return albumMapper.toModel(albumDao.findByNameAndArtist(albumName, artistName).orElseThrow(() -> new RuntimeException("No album")));
    }
}
