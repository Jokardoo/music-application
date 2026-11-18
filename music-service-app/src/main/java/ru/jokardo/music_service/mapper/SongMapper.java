package ru.jokardo.music_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.jokardo.music.contract.model.FindContainsSongResponse;
import ru.jokardo.music.contract.model.FindSongResponse;
import ru.jokardo.music.contract.model.MusicGenre;
import ru.jokardo.music.contract.model.SavedSongFromForeignResourceResponse;
import ru.jokardo.music.contract.model.SongCreateRequest;
import ru.jokardo.music.contract.model.SongCreatedResponse;
import ru.jokardo.music_service.model.Song;
import ru.jokardo.music_service.model.SongMetadata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SongMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "musicianName", source = "musicianName")
    @Mapping(target = "songName", source = "songName")
    @Mapping(target = "musicGenre", source = "musicGenre")
    @Mapping(target = "durationSeconds", ignore = true)
    @Mapping(target = "releaseDate", source = "releaseDate")
    @Mapping(target = "albumName", source = "albumName")
    Song getFromSongCreateRequest(SongCreateRequest request);

    @Mapping(target = "musicianName", source = "musicianName")
    @Mapping(target = "songName", source = "songName")
    SongCreatedResponse getSongCreatedResponseFromSong(Song song);

    @Mapping(target = "musicianName", source = "musicianName")
    @Mapping(target = "songName", source = "songName")
    @Mapping(target = "albumName", source = "albumName")
    @Mapping(target = "releaseDate",  source = "releaseDate", qualifiedByName = "localDateToString")
    @Mapping(target = "musicGenre",   source = "musicGenre",  qualifiedByName = "genreToDtoGenre")
    FindContainsSongResponse getContainsSongResponse(Song song);

    List<FindContainsSongResponse> getContainsSongResponse(List<Song> songs);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "musicianName", source = "musicianName")
    @Mapping(target = "songName", source = "songName")
    @Mapping(target = "musicGenre", ignore = true)
    @Mapping(target = "durationSeconds", source = "duration", qualifiedByName = "timeToSeconds")
    @Mapping(target = "releaseDate", ignore = true)
    @Mapping(target = "albumName", ignore = true)
    Song getByFindSongResponse(FindSongResponse fromResource);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "songId", source = "songId")
    @Mapping(target = "downloadLink", source = " fromResource.downloadUrl")
    @Mapping(target = "imageLink", source = " fromResource.imageUrl")
    SongMetadata getSongMetadata(FindSongResponse fromResource, UUID songId);

    @Mapping(target = "musicianName", source = "musicianName")
    @Mapping(target = "songName", source = "songName")
    SavedSongFromForeignResourceResponse toSavedSongFromForeignResourceResponse(Song song);

    default FindSongResponse getFindSongResponse(String id, String musician, String songName, String resourceName, String duration, String downloadUrl, String imageUrl) {
        return FindSongResponse
                .builder()
                .songIdFromResource(id)
                .musicianName(musician)
                .songName(songName)
                .resourceName(resourceName)
                .duration(duration)
                .downloadUrl(downloadUrl)
                .imageUrl(imageUrl)
                .build();
    }

    @Named("localDateToString")
    default String localDateToString(LocalDate date) {
        return date == null ? null : date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Named("genreToDtoGenre")
    default MusicGenre genreToDtoGenre(MusicGenre genre) {
        if (genre == null) {
            return MusicGenre.OTHER;
        }
        return MusicGenre.valueOf(genre.name());
    }

    @Named("timeToSeconds")
    default Integer timeToSeconds(String time) {
        String[] arr = time.split(":");
        int minutes =Integer.parseInt(arr[0]);
        int seconds = Integer.parseInt(arr[1]);

        return minutes * 60 + seconds;
    }
}
