package com.nikitalipatov.albums.infrastructure.db.mapper;

import com.nikitalipatov.albums.application.model.AlbumModel;
import com.nikitalipatov.albums.infrastructure.db.model.AlbumDbModel;
import com.nikitalipatov.albums.infrastructure.dto.AlbumDto;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {

    public AlbumDto toDto(AlbumModel albumModel) {
        return AlbumDto.builder()
                .id(albumModel.getId())
                .name(albumModel.getName())
                .artist(albumModel.getArtist())
                .artistId(albumModel.getArtistId())
                .playCount(albumModel.getPlayCount())
                .listeners(albumModel.getListeners())
                .releaseDate(albumModel.getReleaseDate())
                .trackIds(albumModel.getTrackIdsList())
                .tagsList(albumModel.getTags())
                .build();
    }

    public AlbumModel toModel(AlbumDbModel albumDbModel) {
        return AlbumModel.builder()
                .id(albumDbModel.getId())
                .name(albumDbModel.getName())
                .artist(albumDbModel.getArtist())
                .artistId(albumDbModel.getArtistId())
                .listeners(albumDbModel.getListeners())
                .playCount(albumDbModel.getPlayCount())
                .releaseDate(albumDbModel.getReleaseDate())
                .tags(albumDbModel.getTagsList())
                .trackIdsList(albumDbModel.getTrackIds())
                .build();
    }

    public AlbumDbModel toAlbumEntity(AlbumModel albumModel) {
        return AlbumDbModel.builder()
                .id(albumModel.getId())
                .name(albumModel.getName())
                .artist(albumModel.getArtist())
                .artistId(albumModel.getArtistId())
                .listeners(albumModel.getListeners())
                .playCount(albumModel.getPlayCount())
                .releaseDate(albumModel.getReleaseDate())
                .trackIds(albumModel.getTrackIdsList())
                .tagsList(albumModel.getTags())
                .build();
    }

}
