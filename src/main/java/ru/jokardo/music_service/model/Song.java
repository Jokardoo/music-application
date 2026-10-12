package ru.jokardo.music_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.jokardo.music_service.dto.MusicGenre;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "songs")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Musician id should not be null!")
    @Column(name = "musician_id" )
    private UUID musicianId;

    @Size(min = 2, max = 100, message = "Song name size should be between 2 and 100 characters!")
    @NotNull(message = "Song name should not be null!")
    @Column(name = "song_name")
    private String songName;

    @Column(name = "music_genre")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private MusicGenre musicGenre;

    @Column(name = "duration_seconds", nullable = false)
    @NotNull(message = "Duration should not be null!")
    @Min(value = 1, message = "Duration must be at least 1 second")
    @Max(value = 7200, message = "Duration must not exceed 2 hours")
    private Integer durationSeconds;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "album_name")
    @Size(max = 100, message = "Album name must not exceed 100 characters")
    private String albumName;

}
