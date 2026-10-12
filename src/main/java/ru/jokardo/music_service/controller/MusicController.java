package ru.jokardo.music_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jokardo.music_service.dto.UserProfile;
import ru.jokardo.music_service.service.AuthServiceClient;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MainController {

    private final AuthServiceClient authServiceClient;

    @GetMapping
    public UserProfile getShortUserInfo(@RequestHeader("userId") UUID userId) {
        return authServiceClient.getUserProfileById(userId);
    }

}
