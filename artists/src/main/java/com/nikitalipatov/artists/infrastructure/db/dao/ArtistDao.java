package com.nikitalipatov.artists.infrastructure.db.dao;

import com.nikitalipatov.artists.infrastructure.db.model.ArtistDbModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistDao extends JpaRepository<ArtistDbModel, String> {
    Optional<ArtistDbModel> findByName(String artistName);
    boolean existsByName(String artistName);
}
