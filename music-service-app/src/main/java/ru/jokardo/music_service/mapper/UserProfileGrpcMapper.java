package ru.jokardo.music_service.mapper;

import org.springframework.stereotype.Component;
import ru.jokardo.auth.contracts.grpc.GetUserProfileByEmailRequest;
import ru.jokardo.auth.contracts.grpc.GetUserProfileByUsernameRequest;
import ru.jokardo.auth.contracts.grpc.GetUserProfileRequest;
import ru.jokardo.auth.contracts.grpc.UserProfileGrpc;
import ru.jokardo.music_service.constants.ApplicationExceptionsConstants;
import ru.jokardo.shared_dtos.auth_service.cache.UserProfile;
import ru.jokardo.shared_dtos.auth_service.enums.RoleEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserProfileGrpcMapper {

    public UserProfile toUserProfile(UserProfileGrpc proto) {

        if (proto == null) {
            throw new IllegalArgumentException(String.format(ApplicationExceptionsConstants.OBJECT_SHOULD_NOT_BE_NULL_PATTERN, "UserProfileGrpc"));
        }

        return UserProfile.builder()
                .id(parseUuid(proto.getId()))
                .username(proto.getUsername())
                .email(emptyToNull(proto.getEmail()))
                .role(parseRole(proto.getRole()))
                .blocked(proto.getBlocked())
                .createdAt(parseDateTime(proto.getCreatedAt()))
                .updatedAt(parseDateTime(proto.getUpdatedAt()))
                .build();
    }

    public GetUserProfileRequest getUserProfileResponse(UUID userId) {
        return GetUserProfileRequest
                .newBuilder()
                .setId(userId.toString())
                .build();
    }

    public GetUserProfileByUsernameRequest getUserProfileByUsernameRequest(String username) {
        return GetUserProfileByUsernameRequest
                .newBuilder()
                .setUsername(username)
                .build();
    }

    public GetUserProfileByEmailRequest getUserProfileByEmailRequest(String email) {
        return GetUserProfileByEmailRequest
                .newBuilder()
                .setEmail(email)
                .build();
    }

    private UUID parseUuid(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return UUID.fromString(value);
    }

    private RoleEnum parseRole(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return RoleEnum.valueOf(value);
    }

    private LocalDateTime parseDateTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalDateTime.parse(value);
    }

    private String emptyToNull(String value) {
        return (value == null || value.isEmpty()) ? null : value;
    }

}
