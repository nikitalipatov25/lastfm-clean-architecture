package com.nikitalipatov.albums.infrastructure.db.dao;

import com.nikitalipatov.albums.infrastructure.db.model.AlbumDbModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumDao extends JpaRepository<AlbumDbModel, String> {
    boolean existsByNameAndArtistId(String albumName, String artistId);
    Optional<AlbumDbModel> findByName(String albumName);
}
