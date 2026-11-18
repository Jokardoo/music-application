package ru.jokardo.music_service.constants;

import lombok.experimental.UtilityClass;
import ru.jokardo.music.contract.model.MusicGenre;
//import ru.jokardo.shared_dtos.music_service.enums.MusicGenre;

import java.time.LocalDate;

@UtilityClass
public class ApplicationTestConstants {

    public static final Integer VALID_DURATION_SECONDS = 300;
    public static final String VALID_ALBUM_NAME = "Valid album";
    public static final String VALID_SONG_NAME = "Valid song name";
    public static final String VALID_MUSICIAN_NAME = "Valid Musician name";
    public static final MusicGenre VALID_MUSIC_GENRE = MusicGenre.LATIN_POP;
    public static final LocalDate VALID_RELEASE_DATE = LocalDate.of(1990, 5,11);
    public static final String VALID_CREATED_AT = "2020-10-10T00:00:00";
    public static final String VALID_UPDATED_AT = "2021-11-11T00:00:00";

    public static final String VALID_USERNAME = "ValidUsername";
    public static final String VALID_EMAIL = "ValidEmail@mail.ru";

    public static final Integer NO_VALID_DURATION_SECONDS = -300;
    public static final String NO_VALID_ALBUM_NAME = "3";
    public static final String NO_VALID_SONG_NAME = "@";
    public static final String NO_VALID_MUSICIAN_NAME = "n";
    public static final LocalDate NO_VALID_RELEASE_DATE = LocalDate.of(2190, 5,11);

}
