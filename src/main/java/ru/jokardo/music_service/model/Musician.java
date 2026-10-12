package ru.jokardo.music_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "musicians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Musician {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotNull(message = "Name should not be null!")
    @Size(min = 1, max = 255, message = "Name size should be between 1 and 255 characters!")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country")
    @Size(max = 100, message = "Country name must not exceed 100 characters")
    private String country;

    @Column(name = "formed_year")
    private Integer formedYear;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

}
