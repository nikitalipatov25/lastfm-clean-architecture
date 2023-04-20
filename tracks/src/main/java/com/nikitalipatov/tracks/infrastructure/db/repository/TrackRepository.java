package com.nikitalipatov.tracks.infrastructure.db.repository;

import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.application.port.TrackGateway;
import com.nikitalipatov.tracks.infrastructure.db.dao.TrackDao;
import com.nikitalipatov.tracks.infrastructure.db.mapper.TrackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrackRepository implements TrackGateway {

    private final TrackDao trackDao;
    private final TrackMapper trackMapper;

    @Override
    public TrackModel save(TrackModel trackModel) {
        var track = trackMapper.toEntity(trackModel);
        return trackMapper.toModel(trackDao.save(track));
    }

    @Override
    public TrackModel getTrackInfo(String artistName, String trackName) {
        return trackMapper.toModel(trackDao.findByNameAndArtist(trackName, artistName).orElseThrow(() -> new RuntimeException("No track")));
    }

    @Override
    public List<TrackModel> getTracksByArtist(String artistName) {
        return trackMapper.toModel(trackDao.findAllByArtist(artistName));
    }

    @Override
    public List<TrackModel> getTracksByGenre(List<String> genreList) {
        return trackMapper.toModel(trackDao.findAllByTagsListIn(genreList));
    }

    @Override
    public List<TrackModel> getTrackByListeners() {
        return trackMapper.toModel(trackDao.findAll(Sort.by(Sort.Direction.DESC, "listeners")));
    }

    @Override
    public List<TrackModel> getTrackByPlayCount() {
        return trackMapper.toModel(trackDao.findAll(Sort.by(Sort.Direction.DESC, "playCount")));
    }
}
