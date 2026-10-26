package ru.jokardo.music_service.util;

import ru.jokardo.music_service.constants.ApplicationTestConstants;
import ru.jokardo.shared_dtos.music_service.request.SongCreateRequest;

public class SongCreateRequestGenerator {

    public static SongCreateRequest generateValidSongCreateRequest() {
        return SongCreateRequest
                .builder()
                .songName(ApplicationTestConstants.VALID_SONG_NAME)
                .musicianName(ApplicationTestConstants.VALID_MUSICIAN_NAME)
                .musicGenre(ApplicationTestConstants.VALID_MUSIC_GENRE)
                .albumName(ApplicationTestConstants.VALID_ALBUM_NAME)
                .releaseDate(ApplicationTestConstants.VALID_RELEASE_DATE)
                .build();
    }

    public static SongCreateRequest generateNoValidSongCreateRequest() {
        return SongCreateRequest
                .builder()
                .songName(ApplicationTestConstants.NO_VALID_SONG_NAME)
                .musicianName(ApplicationTestConstants.NO_VALID_MUSICIAN_NAME)
                .musicGenre(ApplicationTestConstants.VALID_MUSIC_GENRE) //enum -валидный
                .albumName(ApplicationTestConstants.NO_VALID_ALBUM_NAME)
                .releaseDate(ApplicationTestConstants.NO_VALID_RELEASE_DATE)
                .build();
    }

}
