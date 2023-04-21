package com.nikitalipatov.tracks.infrastructure.db.mapper;

import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.infrastructure.db.model.TrackDbModel;
import com.nikitalipatov.tracks.infrastructure.dto.TrackDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
// TODO: 21.04.2023 add mapstruct
public class TrackMapper {

    public TrackDbModel toEntity(TrackModel trackModel) {
        return TrackDbModel.builder()
                .id(trackModel.getId())
                .name(trackModel.getName())
                .listeners(trackModel.getListeners())
                .playCount(trackModel.getPlayCount())
                .albumId(trackModel.getAlbumId())
                .artistId(trackModel.getArtistId())
                .tagsList(trackModel.getTagsList())
                .build();
    }

    public TrackDto toDto(TrackModel trackModel) {
        return TrackDto.builder()
                .id(trackModel.getId())
                .name(trackModel.getName())
                .listeners(trackModel.getListeners())
                .playCount(trackModel.getPlayCount())
                .tagsList(trackModel.getTagsList())
                .albumId(trackModel.getAlbumId())
                .artistId(trackModel.getArtistId())
                .build();
    }

    public List<TrackDto> toDto(List<TrackModel> trackModelList) {
        return trackModelList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public TrackModel toModel(TrackDbModel trackDbModel) {
        return TrackModel.builder()
                .id(trackDbModel.getId())
                .name(trackDbModel.getName())
                .listeners(trackDbModel.getListeners())
                .playCount(trackDbModel.getPlayCount())
                .tagsList(trackDbModel.getTagsList())
                .albumId(trackDbModel.getAlbumId())
                .artistId(trackDbModel.getArtistId())
                .build();
    }

    public List<TrackModel> toModel(List<TrackDbModel> trackDbModelList) {
        return trackDbModelList.stream().map(this::toModel).collect(Collectors.toList());
    }

}
