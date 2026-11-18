package ru.jokardo.music_service.service.util;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.jokardo.music_service.constants.ApplicationExceptionsConstants;
import ru.jokardo.shared_dtos.auth_service.cache.UserProfile;

@Slf4j
@UtilityClass
public class AuthServiceClientUtil {

    public void logFallback(String method, String paramName, String value, Throwable t) {
        if (t instanceof CallNotPermittedException) {
            log.warn("Circuit breaker OPEN for authServiceClient. Fallback on {}({}={}).", method, paramName, value);
        } else {
            log.error("AuthService call failed on {}({}={}).", method, paramName, value, t);
        }
    }

    public UserProfile getDefaultUserProfileWithServerNotAvailableMessage() {
        return UserProfile
                .builder()
                .errorMessage(ApplicationExceptionsConstants.SERVER_NOT_AVAILABLE)
                .build();
    }
}
