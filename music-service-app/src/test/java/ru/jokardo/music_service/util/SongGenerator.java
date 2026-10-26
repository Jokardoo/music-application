package ru.jokardo.music_service.util;

import ru.jokardo.music_service.constants.ApplicationTestConstants;
import ru.jokardo.music_service.model.Song;
import ru.jokardo.shared_dtos.music_service.request.SongCreateRequest;

import java.util.UUID;

public class SongGenerator {

    public static Song generateFromSongCreateRequest(SongCreateRequest request) {
        return Song
                .builder()
                .id(UUID.randomUUID())
                .songName(request.songName())
                .musicianName(request.musicianName())
                .durationSeconds(ApplicationTestConstants.VALID_DURATION_SECONDS)
                .musicGenre(request.musicGenre())
                .albumName(request.albumName())
                .releaseDate(request.releaseDate())
                .build();

    }

}
