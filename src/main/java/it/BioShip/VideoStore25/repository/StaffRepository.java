package it.BioShip.VideoStore25.repository;


import it.BioShip.VideoStore25.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
}
