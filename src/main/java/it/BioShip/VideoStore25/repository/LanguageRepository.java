package it.BioShip.VideoStore25.repository;

import it.BioShip.VideoStore25.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
}
