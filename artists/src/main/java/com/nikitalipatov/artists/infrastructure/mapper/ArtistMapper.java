package com.nikitalipatov.artists.infrastructure.mapper;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.infrastructure.db.model.ArtistDbModel;
import com.nikitalipatov.common.dto.ArtistDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    ArtistDbModel toEntity(ArtistModel artistModel);
    ArtistDto toDto(ArtistModel artistModel);
    ArtistModel toModel(ArtistDbModel artistDbModel);
}
