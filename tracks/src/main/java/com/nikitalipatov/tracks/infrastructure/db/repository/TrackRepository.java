package com.nikitalipatov.tracks.infrastructure.db.repository;

import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.application.port.TrackGateway;
import com.nikitalipatov.tracks.infrastructure.db.dao.TrackDao;
import com.nikitalipatov.tracks.infrastructure.db.mapper.TrackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nikitalipatov.common.enums.ModelDbStatus.ACTIVE;
import static com.nikitalipatov.common.enums.ModelDbStatus.DELETED;

@Repository
@RequiredArgsConstructor
@CacheConfig(cacheNames = "track")
public class TrackRepository implements TrackGateway {

    private final TrackDao trackDao;
    private final TrackMapper trackMapper;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Override
    @CachePut(key = "#trackModel.name")
    public TrackModel save(TrackModel trackModel) {
        var track = trackMapper.toEntity(trackModel);
        return trackMapper.toModel(trackDao.save(track));
    }

    @Override
    @Cacheable(key = "#trackName")
    public TrackModel getTrackInfo(String artistName, String trackName) {
        return trackMapper.toModel(trackDao.findByNameAndArtist(trackName, artistName).orElseThrow(() -> new RuntimeException("No track")));
    }

    @Override
    public List<TrackModel> getTracksByArtist(String artistName) {
        return trackMapper.toModel(trackDao.findAllByArtist(artistName));
    }

    @Override
    public List<TrackModel> getTracksByGenre(List<String> genreList) {
        return trackMapper.toModel(trackDao.findAllByTagsListIn(genreList));
    }

    @Override
    public List<TrackModel> getTrackByListeners() {
        return trackMapper.toModel(trackDao.findAll(Sort.by(Sort.Direction.DESC, "listeners")));
    }

    @Override
    public List<TrackModel> getTrackByPlayCount() {
        return trackMapper.toModel(trackDao.findAll(Sort.by(Sort.Direction.DESC, "playCount")));
    }

    @Override
    public void sendInfo(KafkaMessage kafkaMessage) {
        kafkaTemplate.send("result", kafkaMessage);
    }

    @Override
    public boolean isTrackExists(String trackName, String artistName) {
        return trackDao.existsByNameAndArtist(trackName, artistName);
    }

    @Override
    public void deleteTrack(String artistName, String albumName) {
        var tracks = trackDao.findAllByArtistAndAlbum(artistName, albumName);
        tracks.stream().
                filter(track -> track.getStatus().equals(ACTIVE.name())).
                forEachOrdered(track -> track.setStatus(DELETED.name()));
        trackDao.saveAll(tracks);
    }
}
