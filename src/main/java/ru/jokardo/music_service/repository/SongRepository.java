package ru.jokardo.music_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jokardo.music_service.model.Song;

import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {



}
