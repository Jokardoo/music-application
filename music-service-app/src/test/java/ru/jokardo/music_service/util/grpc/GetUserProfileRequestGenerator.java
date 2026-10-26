package ru.jokardo.music_service.util.grpc;


import ru.jokardo.auth.contracts.grpc.GetUserProfileRequest;

import java.util.UUID;

public class GetUserProfileRequestGenerator {

    public static GetUserProfileRequest generateRequest(UUID id) {
        return GetUserProfileRequest
                .newBuilder()
                .setId(id.toString())
                .build();
    }

}
