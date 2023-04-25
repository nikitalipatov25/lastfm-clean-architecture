package com.nikitalipatov.albums.infrastructure.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

import static com.nikitalipatov.common.enums.ModelDbStatus.ACTIVE;

@Entity
@Table(name = "album")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumDbModel implements Serializable {

    @Id
    private String id;
    private String name;
    private String artistId;
    private int playCount;
    private int listeners;
    @Builder.Default
    private String status = ACTIVE.name();
}
