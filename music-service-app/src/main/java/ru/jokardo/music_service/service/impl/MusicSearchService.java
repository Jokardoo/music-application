package ru.jokardo.music_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.jokardo.music.contract.model.FindSongRequest;
import ru.jokardo.music.contract.model.FindSongResponse;
import ru.jokardo.music_service.mapper.HitmoHtmlParser;

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
        var musician = request.getMusicianName() == null ? "" : request.getMusicianName();
        var songName = request.getSongName() == null ? "" : request.getSongName();

        return hitmoHtmlParser.parse(musician + songName);
    }

    public FindSongResponse getBySongId(String id) {
        return hitmoHtmlParser.getSongById(id);
    }

}
