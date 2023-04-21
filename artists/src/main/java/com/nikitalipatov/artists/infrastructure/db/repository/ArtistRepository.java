package com.nikitalipatov.artists.infrastructure.db.repository;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.application.port.ArtistGateway;
import com.nikitalipatov.artists.infrastructure.db.dao.ArtistDao;
import com.nikitalipatov.artists.infrastructure.db.mapper.ArtistMapper;
import com.nikitalipatov.common.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@CacheConfig(cacheNames = "artist")
public class ArtistRepository implements ArtistGateway {

    private final ArtistDao artistDao;
    private final ArtistMapper artistMapper;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Override
    @CachePut(key = "#artistModel.name")
    public ArtistModel save(ArtistModel artistModel) {
        var artist = artistMapper.toEntity(artistModel);
        return artistMapper.toModel(artistDao.save(artist));
    }

    // TODO: 19.04.2023 custom exception
    @Override
    @Cacheable(key = "#artistName")
    public ArtistModel getArtistInfo(String artistName) {
        return artistMapper.toModel(artistDao.findByName(artistName).orElseThrow(() -> new RuntimeException("No artist")));
    }

    @Override
    public List<ArtistModel> getArtistsChartByListeners() {
        return artistMapper.toModel(artistDao.findAll(Sort.by(Sort.Direction.DESC, "listeners")));
    }

    @Override
    public List<ArtistModel> getArtistsChartByPlayCount() {
        return artistMapper.toModel(artistDao.findAll(Sort.by(Sort.Direction.DESC, "playCount")));
    }

    @Override
    public boolean isArtistExist(String artistId) {
        return artistDao.existsById(artistId);
    }

    @Override
    public void sendInfo(KafkaMessage kafkaMessage) {
        kafkaTemplate.send("result", kafkaMessage);
    }
}
