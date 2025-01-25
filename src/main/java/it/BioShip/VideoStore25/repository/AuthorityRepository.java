package it.BioShip.VideoStore25.repository;


import it.BioShip.VideoStore25.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Byte> {

    Authority findByDefaultAuthorityTrue();

    boolean existsByAuthorityName(String authorityName);

    long countByDefaultAuthorityTrue();

    Set<Authority> findByVisibleTrueAndAuthorityNameIn(Set<String> authorities);
    // SELECT * FROM authority WHERE authority_name IN('ROLE_MODERATOR','ROLE_WRITER')


    Authority findByAuthorityName(String authorityName);


}
