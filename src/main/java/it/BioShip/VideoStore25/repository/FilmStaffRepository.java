package it.BioShip.VideoStore25.repository;

import it.BioShip.VideoStore25.entity.FilmStaff;
import it.BioShip.VideoStore25.entity.FilmStaffId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FilmStaffRepository extends JpaRepository<FilmStaff, FilmStaffId>
{

}
