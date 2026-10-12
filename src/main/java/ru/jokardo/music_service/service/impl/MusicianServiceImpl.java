package ru.jokardo.music_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.jokardo.music_service.model.Musician;
import ru.jokardo.music_service.repository.MusicianRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicianServiceImpl {

    private final MusicianRepository musicianRepository;

    // Данный метод может вызываться только в уже действующей транзакции
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveNewMusicianWithoutInfo(String musicianName) {
        log.debug("Try to save new musician without info. Musician name: {}", musicianName);

        var musician = new Musician();
        musician.setName(musicianName);

        try {
            musicianRepository.save(musician);
        }
        catch (Exception e) {
            throw new RuntimeException("Exception when trying to save new musician!");
        }
        log.debug("Musician {} was successfully saved!", musicianName);
    }

    public boolean existsByMusicianName(String musicianName) {
        return musicianRepository.existsByName(musicianName);
    }

}
