package ru.jokardo.music_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.jokardo.music_service.mapper.HitmoHtmlParser;
import ru.jokardo.shared_dtos.music_service.request.FindSongRequest;
import ru.jokardo.shared_dtos.music_service.response.FindSongResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicSearchService {

    private final HitmoHtmlParser hitmoHtmlParser;

    public List<FindSongResponse> searchFromHitmoz(FindSongRequest request) {

        if (request == null) {
            return List.of();
        }
        var musician = request.musicianName() == null ? "" : request.musicianName();
        var songName = request.songName() == null ? "" : request.songName();
        var query = URLEncoder.encode(musician + " " + songName, StandardCharsets.UTF_8)
                .replace("+", "%20");

        var searchUrl = "https://ru.hitmoz.org/search?q=" + query;

//        return hitmoHtmlParser.parse(searchUrl);
        return hitmoHtmlParser.parse(musician + songName);
    }

}
