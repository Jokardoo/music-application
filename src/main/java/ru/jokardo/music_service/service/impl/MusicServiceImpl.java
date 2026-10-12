package ru.jokardo.music_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jokardo.music_service.repository.SongRepository;
import ru.jokardo.music_service.constants.ApplicationExceptionsConstants;
import ru.jokardo.music_service.mapper.SongMapper;
import ru.jokardo.music_service.service.MusicService;
import ru.jokardo.shared_dtos.music_service.request.SongCreateRequest;
import ru.jokardo.shared_dtos.music_service.response.SongCreatedResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicianServiceImpl musicianService;
    private final SongRepository songRepository;
    private final SongMapper songMapper;

    @Override
    @Transactional
    public SongCreatedResponse createSong(SongCreateRequest songCreateRequest) {
        if (songCreateRequest == null) {
            throw new IllegalArgumentException(ApplicationExceptionsConstants.SONG_CREATE_EXCEPTION_IS_NULL);
        }

        if (!musicianService.existsByMusicianName(songCreateRequest.musicianName())) {
            musicianService.saveNewMusicianWithoutInfo(songCreateRequest.musicianName());
        }

        var songToSave = songMapper.getFromSongCreateRequest(songCreateRequest);
        var savedSong = songRepository.save(songToSave);

        return songMapper.getSongCreatedResponseFromSong(savedSong);
    }
}
