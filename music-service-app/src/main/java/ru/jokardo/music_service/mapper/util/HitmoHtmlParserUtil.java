package ru.jokardo.music_service.mapper.util;

import org.jsoup.nodes.Element;

import java.util.Objects;

public class HitmoHtmlParserUtil {

    private static final String DIV_TRACK__TITLE = "div.track__title";
    private static final String DIV_TRACK__DESC = "div.track__desc";
    private static final String DIV_TRACK__FULLTIME = "div.track__fulltime";
    private static final String HITMOZ_ORG_PATH = "https://ru.hitmoz.org";

    public static String getSongNameFromElement(Element item) {

        return item.selectFirst(DIV_TRACK__TITLE) != null
                ? Objects.requireNonNull(item.selectFirst(DIV_TRACK__TITLE)).text()
                : null;
    }

    public static String getMusicianNameFromElement(Element item) {
        return item.selectFirst(DIV_TRACK__DESC) != null
                ? Objects.requireNonNull(item.selectFirst(DIV_TRACK__DESC)).text()
                : null;
    }

    public static String getDurationFromElement(Element item) {
        return item.selectFirst(DIV_TRACK__FULLTIME) != null
                ? Objects.requireNonNull(item.selectFirst(DIV_TRACK__FULLTIME)).text()
                : null;
    }

    public static String getSongIdFromResource(Element item) {
        return item.attr("data-musid").substring(9);
    }

    public static String getDownloadLinkFromElement(Element item) {
        return item.selectFirst("a.track__download-btn") != null
                ? HITMOZ_ORG_PATH + Objects.requireNonNull(item.selectFirst("a.track__download-btn")).attr("href")
                : null;
    }

    public static String getImageUrlFromElement(Element item) {
        if (item == null) return null;

        var style = item.attr("style");
        int start = style.indexOf("url('");
        int end = style.indexOf("')", start);
        if (start == -1 || end == -1) return null;

        return style.substring(start + 5, end);
    }
}
