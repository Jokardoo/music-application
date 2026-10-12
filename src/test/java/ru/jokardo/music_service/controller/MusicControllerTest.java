package ru.jokardo.music_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.jokardo.music_service.constants.ApplicationPathsConstants;
import ru.jokardo.music_service.dto.UserProfile;
import ru.jokardo.music_service.service.AuthServiceClient;
import ru.jokardo.music_service.util.UserProfileGenerator;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MusicController.class)
public class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthServiceClient authServiceClient;

    private UserProfile userProfile;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userProfile = UserProfileGenerator.generateValidUserProfile();
        userId = UUID.randomUUID();
    }

    @Test
    void getUserProfileById_shouldReturnUserProfile_status200() throws Exception {
        when(authServiceClient.getUserProfileById(any(UUID.class))).thenReturn(userProfile);

        mockMvc.perform(get(ApplicationPathsConstants.MUSIC_USER_PROFILE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userId", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
