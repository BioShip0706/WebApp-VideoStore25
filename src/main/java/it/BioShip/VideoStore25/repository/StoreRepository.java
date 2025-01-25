package it.BioShip.VideoStore25.repository;


import it.BioShip.VideoStore25.entity.Store;
import it.BioShip.VideoStore25.payload.response.CustomerStoreResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StoreRepository extends JpaRepository<Store, Long>
{


    Store findByStoreName(String storeName);

    @Query("SELECT NEW it.BioShip.VideoStore25.payload.response.CustomerStoreResponse" +
            "(s.storeName, COUNT(DISTINCT r.rentalId.customerId.customerId)) " +
            "FROM Store s " +
            "JOIN Inventory i ON s = i.storeId " +
            "JOIN Rental r on i = r.rentalId.inventoryId  " +
            "WHERE s.storeName = :storeName " +
            "GROUP BY s.storeName")
    CustomerStoreResponse countCustomersByStoreName(String storeName);

    boolean existsByStoreName(String storeName);
}
