package com.nikitalipatov.tracks.infrastructure.db.repository;

import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.application.port.TrackGateway;
import com.nikitalipatov.tracks.infrastructure.db.dao.TrackDao;
import com.nikitalipatov.tracks.infrastructure.db.model.TrackDbModel;
import com.nikitalipatov.tracks.infrastructure.mapper.TrackConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nikitalipatov.common.constant.Constants.*;
import static com.nikitalipatov.common.enums.ModelDbStatus.ACTIVE;
import static com.nikitalipatov.common.enums.ModelDbStatus.DELETED;

@Repository
@RequiredArgsConstructor
@CacheConfig(cacheNames = "track")
public class TrackRepository implements TrackGateway {

    private final TrackDao trackDao;
    private final TrackConverter trackConverter;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Override
    @CachePut(key = "#trackModel.id")
    public TrackModel save(TrackModel trackModel) {
        var track = trackConverter.toEntity(trackModel);
        return trackConverter.toModel(trackDao.save(track));
    }

    @Override
    @Cacheable(key = "#trackId")
    public TrackModel getTrackInfo(String trackId) {
        return trackConverter.toModel(getTrackById(trackId));
    }

    @Override
    @Cacheable
    public List<TrackModel> getTracksByArtist(String artistId) {
        return trackConverter.toModel(trackDao.findAllByArtistId(artistId));
    }

    @Override
    @Cacheable
    public List<TrackModel> getTracksByGenre(List<String> genreList) {
        return trackConverter.toModel(trackDao.findAllByTagsListIn(genreList));
    }

    @Override
    @Cacheable
    public List<TrackModel> getTrackByListeners() {
        return trackConverter.toModel(trackDao.findAll(Sort.by(Sort.Direction.DESC, LISTENERS)));
    }

    @Override
    @Cacheable
    public List<TrackModel> getTrackByPlayCount() {
        return trackConverter.toModel(trackDao.findAll(Sort.by(Sort.Direction.DESC, PLAY_COUNT)));
    }

    @Override
    public void sendToAlbum(String artistName, String albumName, String albumId) {
        var message = KafkaMessage.builder()
                .eventType(EventType.LOAD)
                .artistName(artistName)
                .albumName(albumName)
                .albumId(albumId)
                .build();
        kafkaTemplate.send(COMMAND_TOPIC, message);
    }

    @Override
    public boolean isTrackExists(String trackId) {
        return trackDao.existsById(trackId);
    }

    @Override
    public void rollbackTrack(String albumId) {
        var tracks = trackDao.findAllByAlbumId(albumId);
        tracks.stream().
                filter(track -> track.getStatus().equals(ACTIVE.name())).
                forEachOrdered(track -> track.setStatus(DELETED.name()));
        trackDao.saveAll(tracks);
        var message = KafkaMessage.builder()
                .eventType(EventType.ROLLBACK)
                .albumId(albumId)
                .build();
        kafkaTemplate.send(COMMAND_TOPIC, message);
    }

    private TrackDbModel getTrackById(String trackId) {
        return trackDao.findById(trackId)
                .orElseThrow(() -> new ResourceNotFoundException("No such track with name " + trackId));
    }
}
