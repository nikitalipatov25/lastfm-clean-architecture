package com.nikitalipatov.artists.infrastructure.db.repository;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.application.port.ArtistGateway;
import com.nikitalipatov.artists.infrastructure.db.dao.ArtistDao;
import com.nikitalipatov.artists.infrastructure.db.model.ArtistDbModel;
import com.nikitalipatov.artists.infrastructure.mapper.ArtistConverter;
import com.nikitalipatov.common.dto.KafkaMessage;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nikitalipatov.common.constant.Constants.*;

@Repository
@RequiredArgsConstructor
@CacheConfig(cacheNames = "artist")
public class ArtistRepository implements ArtistGateway {

    private final ArtistDao artistDao;
    private final ArtistConverter artistConverter;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Override
    @CachePut(key = "#artistModel.id")
    public ArtistModel save(ArtistModel artistModel) {
        var artist = artistConverter.toEntity(artistModel);
        return artistConverter.toModel(artistDao.save(artist));
    }

    @Override
    @Cacheable(key = "#artistId")
    public ArtistModel getArtistInfo(String artistId) {
        return artistConverter.toModel(getArtistById(artistId));
    }

    @Override
    @Cacheable
    public List<ArtistModel> getArtistsChartByListeners() {
        return artistConverter.toModel(artistDao.findAll(Sort.by(Sort.Direction.DESC, LISTENERS)));
    }

    @Override
    @Cacheable
    public List<ArtistModel> getArtistsChartByPlayCount() {
        return artistConverter.toModel(artistDao.findAll(Sort.by(Sort.Direction.DESC, PLAY_COUNT)));
    }

    @Override
    public boolean isArtistExist(String artistId) {
        return artistDao.existsById(artistId);
    }

    @Override
    public void sendIfError(String albumId) {
        var message = KafkaMessage.builder()
                .albumId(albumId)
                .build();
        kafkaTemplate.send(RESULT_TOPIC, message);
    }

    private ArtistDbModel getArtistById(String artistId) {
        return artistDao.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("No artist with id " + artistId));
    }
}
