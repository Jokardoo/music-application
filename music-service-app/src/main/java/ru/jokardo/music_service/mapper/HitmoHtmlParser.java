package ru.jokardo.music_service.mapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.jokardo.shared_dtos.music_service.response.FindSongResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HitmoHtmlParser {
    public List<FindSongResponse> parse(String query) {

        Document doc = null;
        try {
            doc = Jsoup.connect("https://rus.hitmotop.com/search?q=" + query).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements items = doc.select("body > main.content > div.container > div.content-center.muslist "
                + "> div.content-inner > div.p-info.p-inner > ul.tracks__list").select("li");

        List<FindSongResponse> tracks = new ArrayList<>();
        for (Element item : items) {
            var track = parseTrack(item);
            if (track != null) {
                tracks.add(track);
            }
        }
        return tracks;
    }

    private FindSongResponse parseTrack(Element item) {

        var songName = item.selectFirst("div.track__title") != null
                ? item.selectFirst("div.track__title").text()
                : null;
        var musician = item.selectFirst("div.track__desc") != null
                ? item.selectFirst("div.track__desc").text()
                : null;
        var duration = item.selectFirst("div.track__fulltime") != null
                ? item.selectFirst("div.track__fulltime").text()
                : null;

        var songIdFromResource = item.attr("data-musid").substring(9);
        var downloadUrl = item.selectFirst("a.track__download-btn") != null
                ? "https://ru.hitmoz.org" + item.selectFirst("a.track__download-btn").attr("href")
                : null;
        var imgUrl = parseCoverFromStyle(item.selectFirst("div.track__img"));

        return FindSongResponse
                .builder()
                .songIdFromResource(songIdFromResource)
                .musicianName(musician)
                .songName(songName)
                .resourceName("Hitmoz.org")
                .duration(duration)
                .downloadUrl(downloadUrl)
                .imageUrl(imgUrl)
                .build();
    }

    private String parseCoverFromStyle(Element imgDiv) {
        if (imgDiv == null) return null;

        var style = imgDiv.attr("style");
        int start = style.indexOf("url('");
        int end = style.indexOf("')", start);
        if (start == -1 || end == -1) return null;

        return style.substring(start + 5, end);
    }
}