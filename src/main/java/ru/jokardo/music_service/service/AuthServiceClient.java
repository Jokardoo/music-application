package ru.jokardo.music_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jokardo.auth.contracts.grpc.GetUserProfileRequest;
import ru.jokardo.auth.contracts.grpc.GetUserProfileResponse;
import ru.jokardo.auth.contracts.grpc.UserProfileServiceGrpc;
import ru.jokardo.music_service.dto.UserProfile;
import ru.jokardo.music_service.mapper.UserProfileGrpcMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceClient {

    private final UserProfileGrpcMapper userProfileGrpcMapper;

    private final UserProfileServiceGrpc.UserProfileServiceBlockingStub authStub;

    public UserProfile getUserProfileById(UUID userId) {
        GetUserProfileRequest request = GetUserProfileRequest
                .newBuilder()
                .setId(userId.toString())
                .build();
        GetUserProfileResponse response = authStub.getUserProfile(request);
        return userProfileGrpcMapper.toUserProfile(response.getProfile()) ;
    }
}
