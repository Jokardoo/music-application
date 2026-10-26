package ru.jokardo.music_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jokardo.auth.contracts.grpc.UserProfileServiceGrpc;
import ru.jokardo.music_service.mapper.UserProfileGrpcMapper;
import ru.jokardo.shared_dtos.auth_service.cache.UserProfile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceClient {

    private final UserProfileGrpcMapper userProfileGrpcMapper;

    private final UserProfileServiceGrpc.UserProfileServiceBlockingStub authStub;

    public UserProfile getUserProfileById(UUID userId) {
        var request = userProfileGrpcMapper.getUserProfileResponse(userId);
        var response = authStub.getUserProfile(request);
        return userProfileGrpcMapper.toUserProfile(response.getProfile());
    }

    public UserProfile getUserProfileByUsername(String username) {
        var request = userProfileGrpcMapper.getUserProfileByUsernameRequest(username);
        var response = authStub.getUserProfileByUsername(request);
        return userProfileGrpcMapper.toUserProfile(response.getProfile());
    }

    public UserProfile getUserProfileByEmail(String email) {
        var request = userProfileGrpcMapper.getUserProfileByEmailRequest(email);
        var response = authStub.getUserProfileByEmail(request);
        return userProfileGrpcMapper.toUserProfile(response.getProfile());
    }
}
