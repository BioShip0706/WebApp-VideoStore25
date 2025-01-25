package it.BioShip.VideoStore25.repository;


import it.BioShip.VideoStore25.entity.Film;
import it.BioShip.VideoStore25.entity.Inventory;
import it.BioShip.VideoStore25.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long>
{


    List<Inventory> findByFilmIdAndStoreId(Film film, Store store);
}
