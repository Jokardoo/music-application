package ru.jokardo.music_service.util;

import ru.jokardo.music.contract.model.SongCreateRequest;
import ru.jokardo.music_service.constants.ApplicationTestConstants;
import ru.jokardo.music_service.model.Song;
//import ru.jokardo.shared_dtos.music_service.request.SongCreateRequest;

import java.util.UUID;

public class SongGenerator {

    public static Song generateFromSongCreateRequest(SongCreateRequest request) {
        return Song
                .builder()
                .id(UUID.randomUUID())
                .songName(request.getSongName())
                .musicianName(request.getMusicianName())
                .durationSeconds(ApplicationTestConstants.VALID_DURATION_SECONDS)
                .musicGenre(request.getMusicGenre())
                .albumName(request.getAlbumName())
                .releaseDate(request.getReleaseDate())
                .build();
    }

}
