package ru.jokardo.music_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jokardo.music_service.model.SongMetadata;

import java.util.UUID;

@Repository
public interface SongMetadataRepository extends JpaRepository<SongMetadata, UUID> {

}
