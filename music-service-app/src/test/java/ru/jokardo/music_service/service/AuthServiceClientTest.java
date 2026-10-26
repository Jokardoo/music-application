package ru.jokardo.music_service.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jokardo.auth.contracts.grpc.GetUserProfileByEmailRequest;
import ru.jokardo.auth.contracts.grpc.GetUserProfileByUsernameRequest;
import ru.jokardo.auth.contracts.grpc.GetUserProfileRequest;
import ru.jokardo.auth.contracts.grpc.UserProfileServiceGrpc;
import ru.jokardo.music_service.constants.ApplicationTestConstants;
import ru.jokardo.music_service.mapper.UserProfileGrpcMapper;
import ru.jokardo.music_service.util.grpc.GetUserProfileResponseGenerator;
import ru.jokardo.shared_dtos.auth_service.enums.RoleEnum;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceClientTest {

    private final UserProfileGrpcMapper userProfileGrpcMapper = new UserProfileGrpcMapper();

    @Mock
    private UserProfileServiceGrpc.UserProfileServiceBlockingStub authStub;

    @InjectMocks
    private AuthServiceClient authServiceClient;

    @BeforeEach
    void setUp() {
        authServiceClient = new AuthServiceClient(userProfileGrpcMapper, authStub);
    }

    @Test
    void getUserProfileById_whenIdContains_shouldReturnOk() {
        var id = UUID.randomUUID();
        var response = GetUserProfileResponseGenerator.generateFromUserProfile(id);

        when(authStub.getUserProfile(any(GetUserProfileRequest.class)))
                .thenReturn(response);

        var result = authServiceClient.getUserProfileById(id);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.username()).isEqualTo(ApplicationTestConstants.VALID_USERNAME);
        assertThat(result.email()).isEqualTo(ApplicationTestConstants.VALID_EMAIL);
        assertThat(result.role()).isEqualTo(RoleEnum.USER);
    }

    @Test
    void getUserProfileById_whenIdNoContains_shouldReturnBadRequest() {
        var id = UUID.randomUUID();
        var notFound = Status.NOT_FOUND
                .asRuntimeException();

        when(authStub.getUserProfile(any(GetUserProfileRequest.class)))
                .thenThrow(notFound);

        assertThatThrownBy(() -> authServiceClient.getUserProfileById(id))
                .isInstanceOf(StatusRuntimeException.class);
    }

    @Test
    void getUserProfileByUsername_whenUsername_contains_shouldReturnOk() {
        var response = GetUserProfileResponseGenerator.generateFromUsername(ApplicationTestConstants.VALID_USERNAME);

        when(authStub.getUserProfileByUsername(any(GetUserProfileByUsernameRequest.class)))
                .thenReturn(response);

        var result = authServiceClient.getUserProfileByUsername(ApplicationTestConstants.VALID_USERNAME);

        assertThat(result).isNotNull();
        assertThat(result.id()).isNotNull();
        assertThat(result.username()).isEqualTo(ApplicationTestConstants.VALID_USERNAME);
        assertThat(result.email()).isEqualTo(ApplicationTestConstants.VALID_EMAIL);
        assertThat(result.role()).isEqualTo(RoleEnum.USER);
    }

    @Test
    void getUserProfileById_whenUsernameNoContains_shouldReturnBadRequest() {
        var notFound = Status.NOT_FOUND
                .asRuntimeException();

        when(authStub.getUserProfileByUsername(any(GetUserProfileByUsernameRequest.class)))
                .thenThrow(notFound);

        assertThatThrownBy(() -> authServiceClient.getUserProfileByUsername(ApplicationTestConstants.VALID_USERNAME))
                .isInstanceOf(StatusRuntimeException.class);
    }

    @Test
    void getUserProfileByEmail_whenEmailContains_shouldReturnOk() {
        var response = GetUserProfileResponseGenerator.generateFromEmail(ApplicationTestConstants.VALID_EMAIL);

        when(authStub.getUserProfileByEmail(any(GetUserProfileByEmailRequest.class)))
                .thenReturn(response);

        var result = authServiceClient.getUserProfileByEmail(ApplicationTestConstants.VALID_EMAIL);

        assertThat(result).isNotNull();
        assertThat(result.id()).isNotNull();
        assertThat(result.username()).isEqualTo(ApplicationTestConstants.VALID_USERNAME);
        assertThat(result.email()).isEqualTo(ApplicationTestConstants.VALID_EMAIL);
        assertThat(result.role()).isEqualTo(RoleEnum.USER);
    }

    @Test
    void getUserProfileById_whenEmailNoContains_shouldReturnBadRequest() {
        var notFound = Status.NOT_FOUND
                .asRuntimeException();

        when(authStub.getUserProfileByEmail(any(GetUserProfileByEmailRequest.class)))
                .thenThrow(notFound);

        assertThatThrownBy(() -> authServiceClient.getUserProfileByEmail(ApplicationTestConstants.VALID_EMAIL))
                .isInstanceOf(StatusRuntimeException.class);
    }
}