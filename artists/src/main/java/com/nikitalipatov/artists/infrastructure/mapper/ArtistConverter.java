package com.nikitalipatov.artists.infrastructure.mapper;

import com.nikitalipatov.artists.application.model.ArtistModel;
import com.nikitalipatov.artists.infrastructure.db.model.ArtistDbModel;
import com.nikitalipatov.common.dto.ArtistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArtistConverter {

    private final ArtistMapper artistMapper;

    public ArtistDbModel toEntity(ArtistModel artistModel) {
       return artistMapper.toEntity(artistModel);
    }

    public ArtistDto toDto(ArtistModel artistModel) {
        return artistMapper.toDto(artistModel);
    }

    public List<ArtistDto> toDto(List<ArtistModel> artistModelList) {
        return artistModelList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public ArtistModel toModel(ArtistDbModel artistDbModel) {
        return artistMapper.toModel(artistDbModel);
    }

    public List<ArtistModel> toModel(List<ArtistDbModel> artistDbModelList) {
        return artistDbModelList.stream().map(this::toModel).collect(Collectors.toList());
    }
}
