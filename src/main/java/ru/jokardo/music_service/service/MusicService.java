package ru.jokardo.music_service.service;

import ru.jokardo.shared_dtos.music_service.request.SongCreateRequest;
import ru.jokardo.shared_dtos.music_service.response.SongCreatedResponse;

public interface MusicService {

    SongCreatedResponse createSong(SongCreateRequest songCreateRequest);

}
