package it.BioShip.VideoStore25.repository;

import it.BioShip.VideoStore25.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
