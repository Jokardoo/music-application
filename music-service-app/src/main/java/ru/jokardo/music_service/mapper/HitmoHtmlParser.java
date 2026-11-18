package ru.jokardo.music_service.mapper;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.jokardo.music.contract.model.FindSongResponse;
import ru.jokardo.music_service.mapper.util.HitmoHtmlParserUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HitmoHtmlParser {

    private static final String HITMOTOP_SEARCH_PATH_PATTERN = "https://rus.hitmotop.com/search?q=";
    private static final String HITMOTOP_SONG_BY_ID_PATH_PATTERN = "https://ru.hitmoz.org/song/";

    private static final String HTML_SONG__TRACK_ELEMENT_PATTERN = "div.tracks__item.track[data-musid]";
    private static final String HTML_FOUND_ELEMENTS_PATTERN = "body > main.content > div.container > div.content-center.muslist "
            + "> div.content-inner > div.p-info.p-inner > ul.tracks__list";

    private static final String TRACK_BLOCK_NOT_FOUND_PATTERN = "Track block not found for id = '$s'";

    private final SongMapper songMapper;

    public List<FindSongResponse> parse(String query) {

        Document doc;
        try {
            doc = Jsoup.connect(HITMOTOP_SEARCH_PATH_PATTERN + query).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements items = doc.select(HTML_FOUND_ELEMENTS_PATTERN).select("li");

        List<FindSongResponse> tracks = new ArrayList<>();
        for (Element item : items) {
            var track = parseTrack(item);
            if (track != null) {
                tracks.add(track);
            }
        }
        return tracks;
    }

    public FindSongResponse getSongById(String id) {
        Document doc;

        try {
            doc = Jsoup.connect(HITMOTOP_SONG_BY_ID_PATH_PATTERN + id).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var track = doc.selectFirst(HTML_SONG__TRACK_ELEMENT_PATTERN);

        if (track == null) {
            throw new IllegalStateException(String.format(TRACK_BLOCK_NOT_FOUND_PATTERN, id));
        }

        var musmetaJson = track.attr("data-musmeta");

        var duration = track.selectFirst("div.track__fulltime") != null
                ? track.selectFirst("div.track__fulltime").text()
                : null;

        return songMapper.getFindSongResponse(
                id,
                jsonField(musmetaJson, "artist"),
                jsonField(musmetaJson, "title"),
                "Hitmoz.org",
                duration,
                jsonField(musmetaJson, "url").replace("\\", ""),
                jsonField(musmetaJson, "img").replace("\\", ""));
    }

    private FindSongResponse parseTrack(Element item) {

        return songMapper.getFindSongResponse(
                HitmoHtmlParserUtil.getSongIdFromResource(item),
                HitmoHtmlParserUtil.getMusicianNameFromElement(item),
                HitmoHtmlParserUtil.getSongNameFromElement(item),
                "Hitmoz.org",
                HitmoHtmlParserUtil.getDurationFromElement(item),
                HitmoHtmlParserUtil.getDownloadLinkFromElement(item),
                HitmoHtmlParserUtil.getImageUrlFromElement(item));
    }

    private String jsonField(String json, String field) {
        if (json == null) return null;
        var key = "\"" + field + "\":\"";
        int start = json.indexOf(key);
        if (start == -1) return null;
        start += key.length();
        int end = json.indexOf('"', start);
        if (end == -1) return null;
        return json.substring(start, end);
    }
}