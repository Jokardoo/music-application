package ru.jokardo.music_service.util;

import ru.jokardo.music_service.constants.ApplicationTestConstants;
import ru.jokardo.shared_dtos.auth_service.cache.UserProfile;
import ru.jokardo.shared_dtos.auth_service.enums.RoleEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserProfileGenerator {

    public static UserProfile generateValidUserProfile() {
        return UserProfile
                .builder()
                .id(UUID.randomUUID())
                .role(RoleEnum.USER)
                .email(ApplicationTestConstants.VALID_EMAIL)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .username(ApplicationTestConstants.VALID_USERNAME)
                .blocked(false)
                .build();
    }

}
