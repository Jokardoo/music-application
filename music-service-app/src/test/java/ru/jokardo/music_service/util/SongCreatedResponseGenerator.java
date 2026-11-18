package ru.jokardo.music_service.util;

import ru.jokardo.music.contract.model.SongCreatedResponse;
import ru.jokardo.music_service.model.Song;
//import ru.jokardo.shared_dtos.music_service.response.SongCreatedResponse;

public class SongCreatedResponseGenerator {

    public static SongCreatedResponse generateFromSongCreateRequest(Song song) {
        return SongCreatedResponse
                .builder()
                .songName(song.getSongName())
                .musicianName(song.getMusicianName())
                .build();
    }

}
