package com.nikitalipatov.tracks.infrastructure.db.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import static com.nikitalipatov.common.enums.ModelDbStatus.ACTIVE;

@Entity
@Table(name = "track")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackDbModel implements Serializable {

    @Id
    private String id;
    private String name;
    private int playCount;
    private int listeners;
    private String artistId;
    private String albumId;
    @Builder.Default
    private String status = ACTIVE.name();
    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "track_id"))
    private List<String> tagsList;
}
