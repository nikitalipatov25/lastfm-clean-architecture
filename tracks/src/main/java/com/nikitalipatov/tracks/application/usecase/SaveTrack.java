package com.nikitalipatov.tracks.application.usecase;

import com.nikitalipatov.tracks.application.model.TrackModel;
import com.nikitalipatov.tracks.domain.entity.LocalTrack;

public interface SaveTrack {

    TrackModel save (LocalTrack localTrack);

}
