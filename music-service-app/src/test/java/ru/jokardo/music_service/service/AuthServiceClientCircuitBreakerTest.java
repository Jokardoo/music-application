package ru.jokardo.music_service.service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.jokardo.auth.contracts.grpc.GetUserProfileRequest;
import ru.jokardo.auth.contracts.grpc.UserProfileGrpc;
import ru.jokardo.auth.contracts.grpc.UserProfileServiceGrpc;
import ru.jokardo.music_service.constants.ApplicationExceptionsConstants;
import ru.jokardo.music_service.constants.ApplicationTestConstants;
import ru.jokardo.music_service.mapper.UserProfileGrpcMapper;
import ru.jokardo.music_service.util.grpc.GetUserProfileResponseGenerator;
import ru.jokardo.shared_dtos.auth_service.cache.UserProfile;
import ru.jokardo.shared_dtos.auth_service.enums.RoleEnum;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthServiceClientCircuitBreakerTest {

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @MockitoBean
    private UserProfileGrpcMapper userProfileGrpcMapper;

    @MockitoBean
    private UserProfileServiceGrpc.UserProfileServiceBlockingStub authStub;
    private final String CIRCUIT_BREAKER_NAME = "authServiceClient";

    private UUID userId;

    @BeforeEach
    void setUp() {
        circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME).reset();
        userId = UUID.randomUUID();

        var stubRequest = GetUserProfileRequest.newBuilder()
                .setId(userId.toString())
                .build();

        when(userProfileGrpcMapper.getUserProfileResponse(any(UUID.class)))
                .thenReturn(stubRequest);
    }

    @Test
    void shouldReturnRealProfile_whenGrpcCallSucceeds() {

        var expectedProfile = UserProfile.builder()
                .id(userId)
                .username(ApplicationTestConstants.VALID_USERNAME)
                .email(ApplicationTestConstants.VALID_EMAIL)
                .role(RoleEnum.USER)
                .errorMessage(null)
                .build();

        var grpcResponse = GetUserProfileResponseGenerator.generateFromUserProfile(userId);

        var request = userProfileGrpcMapper.getUserProfileResponse(userId);

        when(authStub.getUserProfile(request)).thenReturn(grpcResponse);
        when(userProfileGrpcMapper.toUserProfile(any(UserProfileGrpc.class))).thenReturn(expectedProfile);

        var actual = authServiceClient.getUserProfileById(userId);

        assertThat(actual.role()).isEqualTo(expectedProfile.role());
        assertThat(actual.email()).isEqualTo(expectedProfile.email());
        assertThat(actual.id()).isEqualTo(expectedProfile.id());
        assertThat(actual.username()).isEqualTo(expectedProfile.username());
        assertThat(actual.errorMessage()).isNull();

        verify(authStub, times(1)).getUserProfile(any(GetUserProfileRequest.class));
    }

    @Test
    void shouldReturnFallback_whenGrpcCallFail() {
        when(authStub.getUserProfile(any(GetUserProfileRequest.class))).thenThrow(new StatusRuntimeException(Status.UNAVAILABLE));

        var response = authServiceClient.getUserProfileById(userId);

        assertThat(response).isNotNull();
        assertThat(response.id()).isNull();
        assertThat(response.username()).isNull();
        assertThat(response.email()).isNull();
        assertThat(response.role()).isNull();
        assertThat(response.errorMessage()).isEqualTo(ApplicationExceptionsConstants.SERVER_NOT_AVAILABLE);
    }

    @Test
    void shouldOpenCircuitBreaker_afterMinimumNumberOfCalls() {

        when(authStub.getUserProfile(any()))
                .thenThrow(new StatusRuntimeException(Status.UNAVAILABLE));

        var cb = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        int minimumNumberOfCalls = cb.getCircuitBreakerConfig().getMinimumNumberOfCalls();

        for (int i = 0; i < minimumNumberOfCalls; i++) {
            authServiceClient.getUserProfileById(userId);
        }

        assertThat(cb.getState()).isEqualTo(CircuitBreaker.State.OPEN);

        clearInvocations(authStub);

        var actual = authServiceClient.getUserProfileById(userId);

        assertThat(actual.errorMessage())
                .isEqualTo(ApplicationExceptionsConstants.SERVER_NOT_AVAILABLE);
        verify(authStub, never()).getUserProfile(any());
    }

    @Test
    void shouldNotThrowNpe_whenUserIdIsNull() {
        when(authStub.getUserProfile(any()))
                .thenThrow(new StatusRuntimeException(Status.UNAVAILABLE));

        var actual = authServiceClient.getUserProfileById(null);

        assertThat(actual.errorMessage()).isEqualTo(ApplicationExceptionsConstants.SERVER_NOT_AVAILABLE);
    }
}
