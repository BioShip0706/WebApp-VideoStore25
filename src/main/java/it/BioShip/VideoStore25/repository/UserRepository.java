package it.BioShip.VideoStore25.repository;


import it.BioShip.VideoStore25.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    //boolean existsByEmailOrUsername(String email, String username);
}
