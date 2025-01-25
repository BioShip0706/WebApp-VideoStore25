package it.BioShip.VideoStore25.repository;


import it.BioShip.VideoStore25.entity.Film;
import it.BioShip.VideoStore25.payload.response.FilmMaxRentResponse;
import it.BioShip.VideoStore25.payload.response.FilmRentResponse;
import it.BioShip.VideoStore25.payload.response.FilmRentableResponse;
import it.BioShip.VideoStore25.payload.response.FilmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Set;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>
{

    @Query("SELECT NEW it.BioShip.VideoStore25.payload.response.FilmResponse" +
            "(f.filmId,f.title,f.description,f.releaseYear,f.languageId.languageName) " +
            "FROM Film f " +
            "WHERE f.languageId.languageId = :languageId")
    List<FilmResponse> findByLanguageId(long languageId);

    @Query("SELECT DISTINCT NEW it.BioShip.VideoStore25.payload.response.FilmRentResponse" +
            "(i.filmId.filmId,f.title,s.storeName) " +
            "FROM Rental r " +
            "JOIN r.rentalId.inventoryId i " +
            "JOIN i.storeId s " +
            "JOIN i.filmId f " +
            "WHERE r.rentalId.customerId.customerId = :customerId")
    List<FilmRentResponse> findAllFilmsRentByOneCustomer(long customerId);

    @Query("SELECT NEW it.BioShip.VideoStore25.payload.response.FilmMaxRentResponse" +
           "(i.filmId.filmId,f.title,COUNT(r.rentalId.inventoryId.inventoryId) as nRents) " +
            "FROM Rental r " +
            "JOIN r.rentalId.inventoryId i " +
            "JOIN i.storeId s " +
            "JOIN i.filmId f " +
            "GROUP BY f.filmId, f.title " +
            "ORDER BY nRents DESC")
    List<FilmMaxRentResponse> filmsWithRentsCount();

    @Query("SELECT NEW it.BioShip.VideoStore25.payload.response.FilmResponse" +
            "(f.filmId, f.title, f.description, f.releaseYear, l.languageName) " +
            "FROM FilmStaff fs " +
            "INNER JOIN fs.filmStaffId.roleId r " +
            "INNER JOIN fs.filmStaffId.staffId s " +
            "INNER JOIN fs.filmStaffId.filmId f " +
            "INNER JOIN f.languageId l " +
            "WHERE r.roleName = 'ACTOR' AND s.staffId IN :actorsIdList " +
            "GROUP BY f.filmId, f.title, f.description, f.releaseYear, l.languageName " +
            "HAVING COUNT(DISTINCT s.staffId) = :setSize ") //l'having serve perchè lui trova tutti film con questi attori passati nel set, (con L'in) lui li filtra e rimangono solo quelli con qualunque attore specificato, l'having è per assicurarsi che dopo il filtro se rimangono 4 allora sono loro per forza
    List<FilmResponse> findFilmsByActorsSet(Set<Long> actorsIdList, int setSize);

    boolean existsByTitle(String title);



    @Query("SELECT NEW it.BioShip.VideoStore25.payload.response.FilmRentableResponse" +
            "(f.title,s.storeName,COUNT(i.inventoryId) AS nStockCopies) " +
            "FROM Film f " +
            "INNER JOIN Inventory i ON f.filmId = i.filmId.filmId " +
            "INNER JOIN Store s ON i.storeId.storeId = s.storeId " +
            "WHERE f.title = :title " +
            "GROUP BY f.title, s.storeName")
    List<FilmRentableResponse> findRentableFilms(String title); //copie in possesso del negozio //PER METODO 10

    @Query("SELECT NEW it.BioShip.VideoStore25.payload.response.FilmRentableResponse" +
            "(f.title,s.storeName,COUNT(i.inventoryId) AS nRentedCopies) " +
            "FROM Film f " +
            "INNER JOIN Inventory i ON f.filmId = i.filmId.filmId " +
            "INNER JOIN Store s ON i.storeId.storeId = s.storeId " +
            "INNER JOIN Rental r ON i.inventoryId = r.rentalId.inventoryId.inventoryId " +
            "WHERE f.title = :title " +
            "AND r.rentalReturn IS NULL " +
            "GROUP BY f.title, s.storeName")
    List<FilmRentableResponse> findRentedFilms(String title); //copie noleggiate //PER METODO 10

}
