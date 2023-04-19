package com.nikitalipatov.artists.infrastructure.db.mapper;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.infrastructure.db.model.ArtistDbModel;
import com.nikitalipatov.artists.infrastructure.dto.ArtistDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArtistMapper {

    // TODO: 19.04.2023 не должно быть domain
    public ArtistDbModel toEntity(ArtistModel artistModel) {
       return ArtistDbModel.builder()
               .id(artistModel.getId())
               .name(artistModel.getName())
               .listeners(artistModel.getListeners())
               .playCount(artistModel.getPlayCount())
               .build();
    }

    public ArtistDto toDto(ArtistModel artistModel) {
        return ArtistDto.builder()
                .id(artistModel.getId())
                .name(artistModel.getName())
                .listeners(artistModel.getListeners())
                .playCount(artistModel.getPlayCount())
                .build();
    }

    public List<ArtistDto> toDto(List<ArtistModel> artistModelList) {
        return artistModelList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public ArtistModel toModel(ArtistDbModel artistDbModel) {
        return ArtistModel.builder()
                .id(artistDbModel.getId())
                .name(artistDbModel.getName())
                .playCount(artistDbModel.getPlayCount())
                .listeners(artistDbModel.getListeners())
                .build();
    }

    public List<ArtistModel> toModel(List<ArtistDbModel> artistDbModelList) {
        return artistDbModelList.stream().map(this::toModel).collect(Collectors.toList());
    }
}
