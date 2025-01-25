package it.BioShip.VideoStore25.repository;


import it.BioShip.VideoStore25.entity.Rental;
import it.BioShip.VideoStore25.entity.RentalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, RentalId>
{




    @Query("SELECT r FROM Rental r  " +
            "INNER JOIN r.rentalId.inventoryId i " +
                    "INNER JOIN i.storeId s " +
                    "WHERE DATE(r.rentalReturn) IS NULL " +
                    "AND i.storeId.storeId = :storeId " +
                    "AND r.rentalId.customerId.customerId = :customerId AND i.filmId.filmId = :filmId")
    List<Rental> findNullRents(long customerId, long storeId, long filmId);

    @Query("SELECT r FROM Rental r  " +
            "INNER JOIN r.rentalId.inventoryId i " +
            "INNER JOIN i.storeId s " +
            "WHERE DATE(r.rentalReturn) IS NULL " +
            "AND i.storeId.storeId = :storeId " +
            "AND i.filmId.filmId = :filmId")
    List<Rental> findNullRentsNoCustomer(long storeId, long filmId);


    @Query("SELECT r FROM Rental r  " +
            "INNER JOIN r.rentalId.inventoryId i " +
            "INNER JOIN i.storeId s " +
            "WHERE i.storeId.storeId = :storeId " +
            "AND i.filmId.filmId = :filmId " +
            "AND r.rentalId.inventoryId.inventoryId NOT IN (:listaIdInventari)")
    List<Rental> findFilteredAvailableRents(long storeId, long filmId, List<Long> listaIdInventari);


    @Query(value = "SELECT COUNT(r.rentalId.inventoryId.inventoryId) " +
            "FROM Rental r  " +
            "JOIN r.rentalId.inventoryId i  JOIN i.storeId s " +
            "WHERE s.storeId = :storeId  " +
            "AND DATE(r.rentalId.rentalDate) BETWEEN :start AND :end") //Ho usato DATE() per contare tutte le 24 ore di quella giornata,
    long countRentalsInDateRangeOfStore(long storeId, LocalDate start, LocalDate end);
}
