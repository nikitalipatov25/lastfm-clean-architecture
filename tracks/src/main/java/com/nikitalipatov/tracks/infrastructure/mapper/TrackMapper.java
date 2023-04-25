package com.nikitalipatov.tracks.infrastructure.mapper;

import com.nikitalipatov.common.dto.TrackDto;
import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.infrastructure.db.model.TrackDbModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackMapper {

    TrackDbModel toEntity(TrackModel trackModel);
    TrackDto toDto(TrackModel trackModel);
    TrackModel toModel(TrackDbModel trackDbModel);
}
