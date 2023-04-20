package com.nikitalipatov.albums.infrastructure.db.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "album")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumDbModel {

    @Id
    private String id;
    private String name;
    private String artist;
    private String artistId;
    private String releaseDate;
    private int playCount;
    private int listeners;
    @ElementCollection
    @CollectionTable(name = "trackId", joinColumns = @JoinColumn(name = "album_id"))
    private List<String> trackIds;
    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "album_id"))
    private List<String> tagsList;
}
