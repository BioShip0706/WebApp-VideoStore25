package it.BioShip.VideoStore25.repository;

import it.BioShip.VideoStore25.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
