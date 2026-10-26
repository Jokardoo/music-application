package ru.jokardo.music_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jokardo.music_service.model.Musician;

import java.util.UUID;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, UUID> {

    Boolean existsByName(String name);

}
