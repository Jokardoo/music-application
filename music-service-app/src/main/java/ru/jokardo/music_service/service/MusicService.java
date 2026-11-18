package ru.jokardo.music_service.service;

import ru.jokardo.music.contract.model.FindContainsSongRequest;
import ru.jokardo.music.contract.model.FindContainsSongsResponseList;
import ru.jokardo.music.contract.model.FindSongRequest;
import ru.jokardo.music.contract.model.FindSongResponseList;
import ru.jokardo.music.contract.model.SaveSongFromForeignResourceRequest;
import ru.jokardo.music.contract.model.SavedSongFromForeignResourceResponse;
import ru.jokardo.music.contract.model.SongCreateRequest;
import ru.jokardo.music.contract.model.SongCreatedResponse;

public interface MusicService {

    SongCreatedResponse createSong(SongCreateRequest songCreateRequest);

    FindSongResponseList getSongList(FindSongRequest request);

    FindContainsSongsResponseList getContainsSong(FindContainsSongRequest request);

    SavedSongFromForeignResourceResponse saveSongFromForeignResource(SaveSongFromForeignResourceRequest request);

    void playTrackById(SaveSongFromForeignResourceRequest request) throws Exception;
}
