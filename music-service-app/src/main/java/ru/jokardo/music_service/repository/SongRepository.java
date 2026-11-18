package ru.jokardo.music_service.repository;

import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.jokardo.music_service.model.Song;

import java.util.List;
import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {


    @Query("""
        SELECT s FROM Song s
        WHERE (:songName IS NULL OR s.songName = :songName)
          AND (:musicianName IS NULL OR s.musicianName = :musicianName)
        """)
    List<Song> findContainsSongs(@Param("musicianName") String musicianName, @Param("songName") String songName);

}
