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
import ru.jokardo.music.contract.model.SongCreateRequest;
import ru.jokardo.music.contract.model.SongCreatedResponse;
import ru.jokardo.music_service.constants.ApplicationPathsConstants;
import ru.jokardo.music_service.model.Song;
import ru.jokardo.music_service.service.AuthServiceClient;
import ru.jokardo.music_service.service.MusicService;
import ru.jokardo.music_service.util.SongCreateRequestGenerator;
import ru.jokardo.music_service.util.SongCreatedResponseGenerator;
import ru.jokardo.music_service.util.SongGenerator;
import ru.jokardo.music_service.util.UserProfileGenerator;
import ru.jokardo.shared_dtos.auth_service.cache.UserProfile;
//import ru.jokardo.shared_dtos.music_service.request.SongCreateRequest;
//import ru.jokardo.shared_dtos.music_service.response.SongCreatedResponse;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MusicController.class)
public class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthServiceClient authServiceClient;

    @MockitoBean
    private MusicService musicService;

    private UserProfile userProfile;
    
    private SongCreateRequest songCreateRequest;
    private Song song;
    private SongCreatedResponse songCreatedResponse;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userProfile = UserProfileGenerator.generateValidUserProfile();
        
        songCreateRequest = SongCreateRequestGenerator.generateValidSongCreateRequest();
        song = SongGenerator.generateFromSongCreateRequest(songCreateRequest);
        songCreatedResponse = SongCreatedResponseGenerator.generateFromSongCreateRequest(song);

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

    @Test
    void createSong_shouldReturn201_whenValidRequest() throws Exception {
        when(musicService.createSong(any(SongCreateRequest.class))).thenReturn(songCreatedResponse);

        mockMvc.perform(post(ApplicationPathsConstants.MUSIC_CREATE_SONG)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(songCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createSong_shouldReturn400_whenNoValidRequest() throws Exception {

        mockMvc.perform(post(ApplicationPathsConstants.MUSIC_CREATE_SONG)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SongCreateRequestGenerator.generateNoValidSongCreateRequest())))
                .andExpect(status().isBadRequest());
    }
}
