package com.nikitalipatov.tracks.infrastructure.db.dao;

import com.nikitalipatov.tracks.infrastructure.db.model.TrackDbModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackDao extends JpaRepository<TrackDbModel, String> {
    Optional<TrackDbModel> findByNameAndArtistId(String trackName, String artistId);
    List<TrackDbModel> findAllByArtistId(String artistName);
    List<TrackDbModel> findAllByTagsListIn(List<String> tagList);
    boolean existsByNameAndArtistId(String trackName, String artistId);
    List<TrackDbModel> findAllByAlbumId(String albumId);
}
