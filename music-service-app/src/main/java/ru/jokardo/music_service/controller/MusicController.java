package ru.jokardo.music_service.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.jokardo.music.contract.api.MusicApi;
import ru.jokardo.music.contract.model.FindContainsSongRequest;
import ru.jokardo.music.contract.model.FindContainsSongsResponseList;
import ru.jokardo.music.contract.model.FindSongRequest;
import ru.jokardo.music.contract.model.FindSongResponseList;
import ru.jokardo.music.contract.model.SaveSongFromForeignResourceRequest;
import ru.jokardo.music.contract.model.SavedSongFromForeignResourceResponse;
import ru.jokardo.music.contract.model.SongCreateRequest;
import ru.jokardo.music.contract.model.SongCreatedResponse;
import ru.jokardo.music_service.service.AuthServiceClient;
import ru.jokardo.music_service.service.MusicService;
import ru.jokardo.shared_dtos.auth_service.cache.UserProfile;

import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
public class MusicController implements MusicApi {

    private final AuthServiceClient authServiceClient;
    private final MusicService musicService;

    @GetMapping("/user-profile")
    public UserProfile getShortUserInfo(@RequestHeader("userId") UUID userId) {
        return authServiceClient.getUserProfileById(userId);
    }

    @Override
    public SongCreatedResponse createSong(SongCreateRequest songCreateRequest) {
        return musicService.createSong(songCreateRequest);
    }

    @Override
    public FindContainsSongsResponseList findContainsSongV1(FindContainsSongRequest filter) {
        return musicService.getContainsSong(filter);
    }

    @Override
    public FindSongResponseList findMusic(FindSongRequest findSongRequest) {
        return musicService.getSongList(findSongRequest);
    }

    @Override
    public SavedSongFromForeignResourceResponse saveSongFromForeignResourceV1(@RequestBody SaveSongFromForeignResourceRequest request) {
        return musicService.saveSongFromForeignResource(request);
    }


    // TEST
    @PostMapping("/play")
    public void playTrackById(@RequestBody @NotNull SaveSongFromForeignResourceRequest request) throws Exception {
        musicService.playTrackById(request);
    }

}
