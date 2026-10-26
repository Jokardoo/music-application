package ru.jokardo.music_service.util.grpc;

import ru.jokardo.auth.contracts.grpc.GetUserProfileByEmailResponse;
import ru.jokardo.auth.contracts.grpc.GetUserProfileByUsernameResponse;
import ru.jokardo.auth.contracts.grpc.GetUserProfileResponse;

import java.util.UUID;

public class GetUserProfileResponseGenerator {

    public static GetUserProfileResponse generateFromUserProfile(UUID id) {
        return GetUserProfileResponse.newBuilder()
                .setProfile(UserProfileGrpcGenerator.generateDefaultWithId(id))
                .build();
    }

    public static GetUserProfileByEmailResponse generateFromEmail(String email) {
        return GetUserProfileByEmailResponse.newBuilder()
                .setProfile(UserProfileGrpcGenerator.generateDefaultWithEmail(email))
                .build();
    }

    public static GetUserProfileByUsernameResponse generateFromUsername(String username) {
        return GetUserProfileByUsernameResponse.newBuilder()
                .setProfile(UserProfileGrpcGenerator.generateDefaultWithUsername(username))
                .build();
    }

}
