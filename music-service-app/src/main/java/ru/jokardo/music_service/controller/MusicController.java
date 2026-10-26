package ru.jokardo.music_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.jokardo.music_service.service.AuthServiceClient;
import ru.jokardo.music_service.service.MusicService;
import ru.jokardo.shared_dtos.auth_service.cache.UserProfile;
import ru.jokardo.shared_dtos.music_service.request.FindSongRequest;
import ru.jokardo.shared_dtos.music_service.request.SongCreateRequest;
import ru.jokardo.shared_dtos.music_service.response.FindSongResponseList;
import ru.jokardo.shared_dtos.music_service.response.SongCreatedResponse;

import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/v1/music")
@RequiredArgsConstructor
public class MusicController {

    private final AuthServiceClient authServiceClient;
    private final MusicService musicService;

    @GetMapping("/user-profile")
    public UserProfile getShortUserInfo(@RequestHeader("userId") UUID userId) {
        return authServiceClient.getUserProfileById(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/song")
    public SongCreatedResponse createSong(
            @RequestBody @Valid SongCreateRequest songCreateRequest) {
        return musicService.createSong(songCreateRequest);
    }

    @GetMapping("/find")
    public FindSongResponseList findSongList(@RequestBody @NotNull @Valid FindSongRequest request) {
        return musicService.getSongList(request);
    }

}
