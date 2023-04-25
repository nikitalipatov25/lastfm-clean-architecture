package com.nikitalipatov.artists.infrastructure.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import static com.nikitalipatov.common.enums.ModelDbStatus.ACTIVE;

@Entity
@Table(name = "artist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistDbModel {

    @Id
    private String id;
    private String name;
    private int playCount;
    private int listeners;
    @Builder.Default
    private String status = ACTIVE.name();
}
