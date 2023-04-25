package com.nikitalipatov.albums.infrastructure.mapper;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.infrastructure.db.model.AlbumDbModel;
import com.nikitalipatov.common.dto.AlbumDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    AlbumDto toDto(AlbumModel albumModel);
    AlbumModel toModel(AlbumDbModel albumDbModel);
    AlbumDbModel toEntity(AlbumModel albumModel);
}
