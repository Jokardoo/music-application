package ru.jokardo.music_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.jokardo.music_service.model.Song;
import ru.jokardo.shared_dtos.music_service.request.SongCreateRequest;
import ru.jokardo.shared_dtos.music_service.response.SongCreatedResponse;

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

}
