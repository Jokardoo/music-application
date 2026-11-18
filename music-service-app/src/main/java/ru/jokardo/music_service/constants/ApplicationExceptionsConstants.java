package ru.jokardo.music_service.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationExceptionsConstants {

    public static final String SONG_CREATE_EXCEPTION_IS_NULL = "'Song create request' should not be null!";
    public static final String SONG_ID_SHOULD_NOT_BE_NULL_EXCEPTION = "Song id from request should not be null!";
    public static final String MUSICIAN_AND_SONG_NAME_SHOULD_NOT_BE_EMPTY_EXCEPTION = "Musician name and song name should not be empty at the same time!";


}
