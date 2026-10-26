package ru.jokardo.music_service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.jokardo.auth.contracts.grpc.UserProfileGrpc;
import ru.jokardo.music_service.constants.ApplicationTestConstants;
import ru.jokardo.shared_dtos.auth_service.enums.RoleEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserProfileGrpcMapperTest {

    private UserProfileGrpcMapper mapper;
    private String createdAt;
    private String updatedAt;

    @BeforeEach
    void setUp() {
        mapper = new UserProfileGrpcMapper();

        createdAt = "2024-01-15T10:30:00";
        updatedAt = "2024-06-20T18:45:30";
    }

    @Test
    void shouldMapAllFields() {

        var id = UUID.randomUUID();

        var proto = UserProfileGrpc.newBuilder()
                .setId(id.toString())
                .setUsername(ApplicationTestConstants.VALID_USERNAME)
                .setEmail(ApplicationTestConstants.VALID_EMAIL)
                .setRole(RoleEnum.USER.toString())
                .setBlocked(true)
                .setCreatedAt(createdAt)
                .setUpdatedAt(updatedAt)
                .build();

        var result = mapper.toUserProfile(proto);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.username()).isEqualTo(ApplicationTestConstants.VALID_USERNAME);
        assertThat(result.email()).isEqualTo(ApplicationTestConstants.VALID_EMAIL);
        assertThat(result.role()).isEqualTo(RoleEnum.USER);
        assertThat(result.blocked()).isTrue();
        assertThat(result.createdAt()).isEqualTo(LocalDateTime.parse(createdAt));
        assertThat(result.updatedAt()).isEqualTo(LocalDateTime.parse(updatedAt));
    }

        @Test
        void shouldReturnNullForEmptyOptionalFields() {
            // given
            var proto = UserProfileGrpc.newBuilder()
                    .setId("")
                    .setUsername("user")
                    .setEmail("")
                    .setRole("")
                    .setCreatedAt("")
                    .setUpdatedAt("")
                    .build();

            // when
            var result = mapper.toUserProfile(proto);

            // then
            assertThat(result.id()).isNull();
            assertThat(result.email()).isNull();
            assertThat(result.role()).isNull();
            assertThat(result.createdAt()).isNull();
            assertThat(result.updatedAt()).isNull();
            assertThat(result.username()).isEqualTo("user");
        }

        @Test
        void shouldReturnNullForBlankFields() {
            // given
            var proto = UserProfileGrpc.newBuilder()
                    .setId("   ")
                    .setUsername("user")
                    .setEmail("   ")   // email обрабатывается emptyToNull, а не isBlank
                    .setRole("  ")
                    .setCreatedAt("\t")
                    .setUpdatedAt("\n")
                    .build();

            // when
            var result = mapper.toUserProfile(proto);

            // then
            assertThat(result.id()).isNull();
            assertThat(result.role()).isNull();
            assertThat(result.createdAt()).isNull();
            assertThat(result.updatedAt()).isNull();
            // email НЕ становится null — emptyToNull реагирует только на isEmpty
            assertThat(result.email()).isEqualTo("   ");
        }

        @Test
        void shouldNotConvertBlankEmailToNull() {
            var proto = UserProfileGrpc.newBuilder()
                    .setUsername("user")
                    .setEmail("   ")
                    .build();

            var result = mapper.toUserProfile(proto);

            assertThat(result.email()).isEqualTo("   ");
        }

        @Test
        @DisplayName("blocked=false маппится корректно")
        void shouldMapBlockedFalse() {
            var proto = UserProfileGrpc.newBuilder()
                    .setUsername("user")
                    .setBlocked(false)
                    .build();

            var result = mapper.toUserProfile(proto);

            assertThat(result.blocked()).isFalse();
        }

        @Test
        @DisplayName("default proto → все поля null/false, username пустой")
        void shouldHandleDefaultProto() {
            // given
            var proto = UserProfileGrpc.getDefaultInstance();

            // when
            var result = mapper.toUserProfile(proto);

            // then
            assertThat(result.id()).isNull();
            assertThat(result.username()).isEmpty();
            assertThat(result.email()).isNull();
            assertThat(result.role()).isNull();
            assertThat(result.blocked()).isFalse();
            assertThat(result.createdAt()).isNull();
            assertThat(result.updatedAt()).isNull();
        }

        @Test
        void shouldThrowOnInvalidUuid() {
            var proto = UserProfileGrpc.newBuilder()
                    .setId("not-a-uuid")
                    .setUsername("user")
                    .build();

            assertThatThrownBy(() -> mapper.toUserProfile(proto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void shouldThrowOnUnknownRole() {
            var proto = UserProfileGrpc.newBuilder()
                    .setUsername("user")
                    .setRole("SUPER_HERO")
                    .build();

            assertThatThrownBy(() -> mapper.toUserProfile(proto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void shouldThrowOnInvalidDateTime() {
            var proto = UserProfileGrpc.newBuilder()
                    .setUsername("user")
                    .setCreatedAt("not-a-date")
                    .build();

            assertThatThrownBy(() -> mapper.toUserProfile(proto))
                    .isInstanceOf(DateTimeParseException.class);
        }

        @Test
        void shouldThrowOnDateWithoutTime() {
            var proto = UserProfileGrpc.newBuilder()
                    .setUsername("user")
                    .setCreatedAt("2024-01-15")   // только дата, без T00:00:00
                    .build();

            assertThatThrownBy(() -> mapper.toUserProfile(proto))
                    .isInstanceOf(DateTimeParseException.class);
        }

        @Test
        void shouldBuildRequestWithId() {
            var id = UUID.randomUUID();

            var request = mapper.getUserProfileResponse(id);

            assertThat(request).isNotNull();
            assertThat(request.getId()).isEqualTo(id.toString());
        }

        @Test
        void shouldThrowOnNullId() {
            assertThatThrownBy(() -> mapper.getUserProfileResponse(null))
                    .isInstanceOf(NullPointerException.class);
        }


        @Test
        void shouldBuildRequestWithUsername() {
            var request = mapper.getUserProfileByUsernameRequest("john_doe");

            assertThat(request).isNotNull();
            assertThat(request.getUsername()).isEqualTo("john_doe");
        }

        @Test
        void shouldHandleEmptyUsername() {
            var request = mapper.getUserProfileByUsernameRequest("");

            assertThat(request.getUsername()).isEmpty();
        }


        @Test
        void shouldBuildRequestWithEmail() {
            var request = mapper.getUserProfileByEmailRequest("john@example.com");

            assertThat(request).isNotNull();
            assertThat(request.getEmail()).isEqualTo("john@example.com");
        }

        @Test
        void shouldHandleEmptyEmail() {
            var request = mapper.getUserProfileByEmailRequest("");

            assertThat(request.getEmail()).isEmpty();
        }
}