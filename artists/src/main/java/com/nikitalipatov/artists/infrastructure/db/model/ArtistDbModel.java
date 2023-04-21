package com.nikitalipatov.artists.infrastructure.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
}
