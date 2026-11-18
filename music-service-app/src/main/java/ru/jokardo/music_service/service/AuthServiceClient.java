package ru.jokardo.music_service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jokardo.auth.contracts.grpc.UserProfileServiceGrpc;
import ru.jokardo.music_service.mapper.UserProfileGrpcMapper;
import ru.jokardo.music_service.service.util.AuthServiceClientUtil;
import ru.jokardo.shared_dtos.auth_service.cache.UserProfile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceClient {

    private final UserProfileGrpcMapper userProfileGrpcMapper;

    private final UserProfileServiceGrpc.UserProfileServiceBlockingStub authStub;

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "getUserProfileByIdFallback")
    public UserProfile getUserProfileById(UUID userId) {
        var request = userProfileGrpcMapper.getUserProfileResponse(userId);
        var response = authStub.getUserProfile(request);
        return userProfileGrpcMapper.toUserProfile(response.getProfile());
    }

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "getUserProfileByUsernameFallback")
    public UserProfile getUserProfileByUsername(String username) {
        var request = userProfileGrpcMapper.getUserProfileByUsernameRequest(username);
        var response = authStub.getUserProfileByUsername(request);
        return userProfileGrpcMapper.toUserProfile(response.getProfile());
    }

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "getUserProfileByEmailFallback")
    public UserProfile getUserProfileByEmail(String email) {
        var request = userProfileGrpcMapper.getUserProfileByEmailRequest(email);
        var response = authStub.getUserProfileByEmail(request);
        return userProfileGrpcMapper.toUserProfile(response.getProfile());
    }

    //FallBack методы

    public UserProfile getUserProfileByIdFallback(UUID userId, Throwable t) {
        AuthServiceClientUtil.logFallback("getUserProfileById", "userId", String.valueOf(userId), t);

        return AuthServiceClientUtil.getDefaultUserProfileWithServerNotAvailableMessage();
    }

    public UserProfile getUserProfileByUsernameFallback(String username, Throwable t) {
        AuthServiceClientUtil.logFallback("getUserProfileByUsername", "username", username, t);

        return AuthServiceClientUtil.getDefaultUserProfileWithServerNotAvailableMessage();
    }

    public UserProfile getUserProfileByEmailFallback(String email, Throwable t) {
        AuthServiceClientUtil.logFallback("getUserProfileByEmail", "email", email, t);

        return AuthServiceClientUtil.getDefaultUserProfileWithServerNotAvailableMessage();
    }
}
