package ru.jokardo.music_service.util.grpc;

import ru.jokardo.auth.contracts.grpc.UserProfileGrpc;
import ru.jokardo.music_service.constants.ApplicationTestConstants;
import ru.jokardo.shared_dtos.auth_service.enums.RoleEnum;

import java.util.UUID;

public class UserProfileGrpcGenerator {

    public static UserProfileGrpc generateDefaultWithId(UUID id) {
        return UserProfileGrpc.newBuilder()
                .setBlocked(false)
                .setId(id.toString())
                .setEmail(ApplicationTestConstants.VALID_EMAIL)
                .setRole(RoleEnum.USER.toString())
                .setUsername(ApplicationTestConstants.VALID_USERNAME)
                .setCreatedAt(ApplicationTestConstants.VALID_CREATED_AT)
                .setUpdatedAt(ApplicationTestConstants.VALID_UPDATED_AT)
                .build();
    }

    public static UserProfileGrpc generateDefaultWithUsername(String username) {
        return UserProfileGrpc.newBuilder()
                .setBlocked(false)
                .setId(UUID.randomUUID().toString())
                .setEmail(ApplicationTestConstants.VALID_EMAIL)
                .setRole(RoleEnum.USER.toString())
                .setUsername(username)
                .setCreatedAt(ApplicationTestConstants.VALID_CREATED_AT)
                .setUpdatedAt(ApplicationTestConstants.VALID_UPDATED_AT)
                .build();
    }

    public static UserProfileGrpc generateDefaultWithEmail(String email) {
        return UserProfileGrpc.newBuilder()
                .setBlocked(false)
                .setId(UUID.randomUUID().toString())
                .setEmail(email)
                .setRole(RoleEnum.USER.toString())
                .setUsername(ApplicationTestConstants.VALID_USERNAME)
                .setCreatedAt(ApplicationTestConstants.VALID_CREATED_AT)
                .setUpdatedAt(ApplicationTestConstants.VALID_UPDATED_AT)
                .build();
    }

}
