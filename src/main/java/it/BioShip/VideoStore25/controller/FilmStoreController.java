package it.BioShip.VideoStore25.controller;

import it.BioShip.VideoStore25.payload.request.FilmRequest;
import it.BioShip.VideoStore25.payload.request.RentalRequest;
import it.BioShip.VideoStore25.service.FilmService;
import it.BioShip.VideoStore25.service.RentalService;
import it.BioShip.VideoStore25.service.StoreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("film")
@Validated
//@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://127.0.0.1:5500")
public class FilmStoreController
{
    private final FilmService filmService;
    private final StoreService storeService;
    private final RentalService rentalService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-film/{filmId}") //METODO 1
    public ResponseEntity<?> updateFilm(@PathVariable @Min(1) long filmId, @RequestBody @Valid FilmRequest filmRequest)
    {
        return filmService.updateFilm(filmId,filmRequest);
    }


    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/find-films-by-language/{languageId}") //METODO 2
    public ResponseEntity<?> findFilmsByLanguage(@PathVariable @Min(1) long languageId)
    {
        return filmService.findFilmsByLanguage(languageId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add-film-to-store/{storeId}/{filmId}") //METODO 3
    public ResponseEntity<?> addFilmToStore(@PathVariable @Min(1) long storeId, @PathVariable @Min(1) long filmId)
    {
        return filmService.addFilmToStore(storeId,filmId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/count-customers-by-store/{storeName}") //METODO 4
    public ResponseEntity<?> countCustomersByStore(@PathVariable  @NotBlank String storeName)
    {
        return storeService.countCustomersByStore(storeName);
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @PutMapping("/add-update-rental/{ReturnOrRent}") //METODO 5
    public ResponseEntity<?> addUpdateRental(@RequestBody @Valid RentalRequest request, @PathVariable @NotBlank String ReturnOrRent)
    {
        return rentalService.addUpdateRental(request,ReturnOrRent);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/count-rentals-in-date-range-by-store/{storeId}") //METODO 6
    public ResponseEntity<?> countRentalsInDateRangeByStore(@PathVariable  long storeId, @RequestParam LocalDate start, @RequestParam LocalDate end)
    {
        return rentalService.countRentalsInDateRangeByStore(storeId, start, end);
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')") //admin per un altro customer
    @GetMapping("/find-all-films-rent-by-one-customer/{customerId}") //METODO 7
    public ResponseEntity<?> findAllFilmsRentByOneCustomer(@PathVariable @Min(1) long customerId)
    {
        return filmService.findAllFilmsRentByOneCustomer(customerId);
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/find-film-with-max-number-of-rent") //METODO 8
    public ResponseEntity<?> findFilmWithMaxNumberOfRent()
    {
        return filmService.findFilmWithMaxNumberOfRent();
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/find-films-by-actors/") //METODO 9
    public ResponseEntity<?> findFilmsByActors(@RequestParam Set<Long> actorsIdList)
    {
        return filmService.findFilmsByActors(actorsIdList);
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/find-rentable-films/") //METODO 10
    public ResponseEntity<?> findRentableFilms(@RequestParam @NotBlank String title)
    {
        return filmService.findRentableFilms(title);
    }

}
