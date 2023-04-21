package com.nikitalipatov.albums.infrastructure.db.repository;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.application.port.AlbumGateway;
import com.nikitalipatov.albums.infrastructure.db.dao.AlbumDao;
import com.nikitalipatov.albums.infrastructure.db.mapper.AlbumMapper;
import com.nikitalipatov.common.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@CacheConfig(cacheNames = "album")
public class AlbumRepository implements AlbumGateway {

    private final AlbumDao albumDao;
    private final AlbumMapper albumMapper;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Override
//    @CachePut(key = "#albumModel.name")
    public AlbumModel save(AlbumModel albumModel) {
        var album = albumMapper.toAlbumEntity(albumModel);
        return albumMapper.toModel(albumDao.save(album));
    }

    // TODO: 19.04.2023 custom exception
    @Override
//    @Cacheable(key = "#albumName")
    public AlbumModel getAlbumInfo(String artistName, String albumName) {
        return albumMapper.toModel(albumDao.findByNameAndArtist(albumName, artistName).orElseThrow(() -> new RuntimeException("No album")));
    }

    @Override
    public boolean isAlbumExists(String artistName, String albumName) {
        return albumDao.existsAlbumDbModelByNameAndArtist(albumName, artistName);
    }

    @Override
    public void delete(String artistName, String albumName) {
        albumDao.deleteByNameAndArtist(artistName, albumName);
    }

    @Override
    public void sendInfo(String artistId, String albumId) {
        var message = KafkaMessage.builder()
                .albumId(albumId)
                .artistId(artistId)
                .build();
        kafkaTemplate.send("result", message);
    }
}
