package ru.jokardo.music_service.util;

import ru.jokardo.music_service.dto.UserProfile;
import ru.jokardo.music_service.enums.RoleEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserProfileGenerator {

    public static final String VALID_EMAIL = "valid-email@mail.ru";
    public static final String VALID_USERNAME = "ValidUsername";

    public static UserProfile generateValidUserProfile() {
        return UserProfile
                .builder()
                .id(UUID.randomUUID())
                .role(RoleEnum.USER)
                .email(VALID_EMAIL)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .username(VALID_USERNAME)
                .blocked(false)
                .build();
    }

}
