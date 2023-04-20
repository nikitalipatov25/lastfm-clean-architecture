package com.nikitalipatov.tracks.infrastructure.db.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "track")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackDbModel {

    @Id
    private String id;
    private String name;
    private int duration;
    private int playCount;
    private int listeners;
    private String artist;
    private String artistId;
    private String album;
    private String albumId;
    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "track_id"))
    private List<String> tagsList;
}
