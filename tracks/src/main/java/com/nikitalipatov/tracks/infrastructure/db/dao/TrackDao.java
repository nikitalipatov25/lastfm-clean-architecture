package com.nikitalipatov.tracks.infrastructure.db.dao;

import com.nikitalipatov.tracks.infrastructure.db.model.TrackDbModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackDao extends JpaRepository<TrackDbModel, String> {
    Optional<TrackDbModel> findByNameAndArtist(String trackName, String artistName);
    List<TrackDbModel> findAllByArtist(String artistName);
    List<TrackDbModel> findAllByTagsListIn(List<String> tagList);
}
