package com.nikitalipatov.tracks.infrastructure.mapper;

import com.nikitalipatov.common.dto.TrackDto;
import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.infrastructure.db.model.TrackDbModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrackConverter {

    private final TrackMapper trackMapper;

    public TrackDbModel toEntity(TrackModel trackModel) {
        return trackMapper.toEntity(trackModel);
    }

    public TrackDto toDto(TrackModel trackModel) {
        return trackMapper.toDto(trackModel);
    }

    public List<TrackDto> toDto(List<TrackModel> trackModelList) {
        return trackModelList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public TrackModel toModel(TrackDbModel trackDbModel) {
        return trackMapper.toModel(trackDbModel);
    }

    public List<TrackModel> toModel(List<TrackDbModel> trackDbModelList) {
        return trackDbModelList.stream().map(this::toModel).collect(Collectors.toList());
    }

}
