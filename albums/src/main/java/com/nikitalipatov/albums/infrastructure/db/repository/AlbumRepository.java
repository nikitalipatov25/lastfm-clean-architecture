package com.nikitalipatov.albums.infrastructure.db.repository;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.application.port.AlbumGateway;
import com.nikitalipatov.albums.infrastructure.db.dao.AlbumDao;
import com.nikitalipatov.albums.infrastructure.db.model.AlbumDbModel;
import com.nikitalipatov.albums.infrastructure.mapper.AlbumMapper;
import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import static com.nikitalipatov.common.constant.Constants.COMMAND_TOPIC;
import static com.nikitalipatov.common.constant.Constants.RESULT_TOPIC;
import static com.nikitalipatov.common.enums.ModelDbStatus.ACTIVE;
import static com.nikitalipatov.common.enums.ModelDbStatus.DELETED;

@Repository
@RequiredArgsConstructor
@CacheConfig(cacheNames = "album")
public class AlbumRepository implements AlbumGateway {

    private final AlbumDao albumDao;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final AlbumMapper albumMapper;

    @Override
    @CachePut(key = "#albumModel.id")
    public AlbumModel save(AlbumModel albumModel) {
        var album = albumMapper.toEntity(albumModel);
        return albumMapper.toModel(albumDao.save(album));
    }

    @Override
    @Cacheable(key = "#albumId")
    public AlbumModel findAlbum(String albumId) {
        return albumMapper.toModel(getAlbumById(albumId));
    }

    @Override
    public boolean isAlbumExists(String albumId) {
        return albumDao.existsById(albumId);
    }

    @Override
    public void rollback(String albumId) {
        var album = getAlbumById(albumId);
        if (album.getStatus().equals(ACTIVE.name())) {
            album.setStatus(DELETED.name());
            albumDao.save(album);
        }
    }

    @Override
    public void sendIfError(String albumId) {
        var message = KafkaMessage.builder()
                .eventType(EventType.ROLLBACK)
                .albumId(albumId)
                .build();
        kafkaTemplate.send(RESULT_TOPIC, message);
    }

    @Override
    public void sendToArtist(String artistName, String albumId) {
        var message = KafkaMessage.builder()
                .eventType(EventType.LOAD)
                .artistName(artistName)
                .albumId(albumId)
                .build();
        kafkaTemplate.send(COMMAND_TOPIC, message);
    }

    @Override
    public void loadTracks(String artistName, String albumName) {
        var message = KafkaMessage.builder()
                .eventType(EventType.LOAD)
                .artistName(artistName)
                .albumName(albumName)
                .build();
        kafkaTemplate.send(RESULT_TOPIC, message);
    }

    private AlbumDbModel getAlbumById(String albumId) {
        return albumDao.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("No such album with id " + albumId));
    }
}
