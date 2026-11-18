package ru.jokardo.music_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jokardo.music.contract.model.FindContainsSongRequest;
import ru.jokardo.music.contract.model.FindContainsSongsResponseList;
import ru.jokardo.music.contract.model.FindSongRequest;
import ru.jokardo.music.contract.model.FindSongResponseList;
import ru.jokardo.music.contract.model.MusicGenre;
import ru.jokardo.music.contract.model.SaveSongFromForeignResourceRequest;
import ru.jokardo.music.contract.model.SavedSongFromForeignResourceResponse;
import ru.jokardo.music.contract.model.SongCreateRequest;
import ru.jokardo.music.contract.model.SongCreatedResponse;
import ru.jokardo.music_service.constants.ApplicationExceptionsConstants;
import ru.jokardo.music_service.mapper.SongMapper;
import ru.jokardo.music_service.model.Song;
import ru.jokardo.music_service.repository.SongMetadataRepository;
import ru.jokardo.music_service.repository.SongRepository;
import ru.jokardo.music_service.service.MusicService;
import ru.jokardo.music_service.service.player.MusicPlayer;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicSearchService musicSearchService;
    private final MusicianServiceImpl musicianService;
    private final SongRepository songRepository;
    private final SongMetadataRepository songMetadataRepository;
    private final SongMapper songMapper;

    @Override
    @Transactional
    public SongCreatedResponse createSong(SongCreateRequest songCreateRequest) {
        if (songCreateRequest == null) {
            throw new IllegalArgumentException(ApplicationExceptionsConstants.SONG_CREATE_EXCEPTION_IS_NULL);
        }

        if (!musicianService.existsByMusicianName(songCreateRequest.getMusicianName())) {
            musicianService.saveNewMusicianWithoutInfo(songCreateRequest.getMusicianName());
        }

        var songToSave = songMapper.getFromSongCreateRequest(songCreateRequest);
        var savedSong = songRepository.save(songToSave);

        return songMapper.getSongCreatedResponseFromSong(savedSong);
    }

    @Override
    public FindSongResponseList getSongList(FindSongRequest request) {
        if (request == null) {
            return FindSongResponseList
                    .builder()
                    .songResponseList(List.of())
                    .build();
        } else if (request.getSongName() == null && request.getMusicianName() == null) {
            throw new IllegalArgumentException(ApplicationExceptionsConstants.MUSICIAN_AND_SONG_NAME_SHOULD_NOT_BE_EMPTY_EXCEPTION);
        }
        return FindSongResponseList
                .builder()
                .songResponseList(musicSearchService.searchFromHitmoz(request))
                .build();
    }

@Override
public FindContainsSongsResponseList getContainsSong(FindContainsSongRequest request) {

    if (request == null) {
        return FindContainsSongsResponseList
                .builder()
                .containsSongs(List.of())
                .build();
    } else if (request.getSongName() == null && request.getMusicianName() == null) {
        throw new IllegalArgumentException(ApplicationExceptionsConstants.MUSICIAN_AND_SONG_NAME_SHOULD_NOT_BE_EMPTY_EXCEPTION);
    }

    var songs = songRepository.findContainsSongs(request.getMusicianName(), request.getSongName());

    var response = songMapper.getContainsSongResponse(songs);

    return FindContainsSongsResponseList
            .builder()
            .containsSongs(response)
            .build();
}

    @Override
    @Transactional
    public SavedSongFromForeignResourceResponse saveSongFromForeignResource(SaveSongFromForeignResourceRequest request) {
        if (request == null || request.getSongId() == null) {
            throw new IllegalArgumentException(ApplicationExceptionsConstants.SONG_ID_SHOULD_NOT_BE_NULL_EXCEPTION);
        }
        var fromResource = musicSearchService.getBySongId(request.getSongId());
        var toSave = songMapper.getByFindSongResponse(fromResource);

        updateUnknownSongDataToValid(toSave);

        if (!musicianService.existsByMusicianName(toSave.getMusicianName())) {
            musicianService.saveNewMusicianWithoutInfo(toSave.getMusicianName());
        }

        var saved = songRepository.save(toSave);

        var songMetadata = songMapper.getSongMetadata(fromResource, saved.getId());
        songMetadataRepository.save(songMetadata);

        return songMapper.toSavedSongFromForeignResourceResponse(saved);
    }

    @Override
    public void playTrackById(SaveSongFromForeignResourceRequest request) {
        if (request == null || request.getSongId() == null) {
            throw new IllegalArgumentException(ApplicationExceptionsConstants.SONG_ID_SHOULD_NOT_BE_NULL_EXCEPTION);
        }
        var fromResource = musicSearchService.getBySongId(request.getSongId());
        try {
            MusicPlayer.playTrackFromResource(fromResource.getDownloadUrl());
        } catch (Exception e) {
//            throw new RuntimeException(e);
        }
    }

    private void updateUnknownSongDataToValid(Song song) {
        if (song.getMusicGenre() == null) {
            song.setMusicGenre(MusicGenre.UNKNOWN);
        }
        if (song.getAlbumName() == null) {
            song.setAlbumName("unknown");
        }
    }
}
