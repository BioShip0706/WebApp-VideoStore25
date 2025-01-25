package it.BioShip.VideoStore25.service;

import it.BioShip.VideoStore25.entity.*;
import it.BioShip.VideoStore25.exception.ResourceNotFoundException;
import it.BioShip.VideoStore25.payload.request.RentalRequest;
import it.BioShip.VideoStore25.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService
{
    private final FilmRepository filmRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;
    private final StoreRepository storeRepository;
    private final InventoryRepository inventoryRepository;
    private final CustomerRepository customerRepository;
    private final RentalRepository rentalRepository;


    @Transactional
    public ResponseEntity<?> addUpdateRental(RentalRequest request, String scelta)
    {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Customer","Id", request.getCustomerId()));
        Store store =  storeRepository.findById(request.getStoreId()).orElseThrow(() -> new ResourceNotFoundException("Store","Id", request.getStoreId()));
        Film film = filmRepository.findById(request.getFilmId()).orElseThrow(() -> new ResourceNotFoundException("Film","Id", request.getStoreId()));

        List<Inventory> inventories = inventoryRepository.findByFilmIdAndStoreId(film,store); //cerco un inventario con il film, me ne basta uno,
                                                                                            // almeno una copia disponibile di quel film in quel specifico store

        if (inventories.isEmpty())
        {
            return new ResponseEntity("Film " + film.getTitle() + " not available or present in store " + store.getStoreName(),HttpStatus.NOT_FOUND);
        }

        if(scelta.equalsIgnoreCase("Return"))
        {
            List<Rental> specificRentalWithNullReturn = rentalRepository.findNullRents(customer.getCustomerId(), store.getStoreId(), film.getFilmId()); //trovo un rent di quel film, di quello store, con il mio id customer da restituire, se ce ne sono due restituisco il primo tanto è uguale
            if(specificRentalWithNullReturn.isEmpty())
            {
                return new ResponseEntity<>(   customer.getFirstname()+ ", You have no copies of the film: " + film.getTitle() + " from the store: " + store.getStoreName() + " to return!",HttpStatus.NOT_FOUND);
            }
            else
            {
                specificRentalWithNullReturn.get(0).setRentalReturn(LocalDateTime.now());
                return new ResponseEntity("You returned the film " + film.getTitle() + " from Store " + store.getStoreName(), HttpStatus.OK);
            }


        }

        if(scelta.equalsIgnoreCase("Rent"))
        {
            List<Rental> rentalsWithNullReturnFromInventoriesList = rentalRepository.findNullRentsNoCustomer(store.getStoreId(), film.getFilmId()); //cerco i rent null di quel film di quello store SENZA CUSTOMER, TUTTI
            if(rentalsWithNullReturnFromInventoriesList.size() == inventories.size()) //se ho due inventari e due rental con null vuol dire che sono entrambi occupati e niente
            {
                return new ResponseEntity<>(   customer.getFirstname()+ ", No copies of the film: " + film.getTitle() + " from the store: " + store.getStoreName() + " available to rent!",HttpStatus.NOT_FOUND);

            }
            else
            {
                List<Long> listaIdInventari = new ArrayList<>(); //lista che andrà a contenere tutti gli InventoryId dei rent con return a null, cosi poi li filtro via

                for (int i = 0; i < rentalsWithNullReturnFromInventoriesList.size(); i++)
                {
                    listaIdInventari.add(rentalsWithNullReturnFromInventoriesList.get(i).getRentalId().getInventoryId().getInventoryId());
                }

                List<Rental> availableRentals;

                if(!listaIdInventari.isEmpty())
                {
                    availableRentals = rentalRepository.findFilteredAvailableRents(store.getStoreId(),film.getFilmId(), listaIdInventari); //i rental disponibili, prendo il primo e lo rento
                    Rental newRental = new Rental(new RentalId(customer,availableRentals.get(0).getRentalId().getInventoryId(), LocalDateTime.now()),null);
                    rentalRepository.save(newRental);
                    return new ResponseEntity("You rented the film " + film.getTitle() + " from " + store.getStoreName(),HttpStatus.OK);
                }
                else
                {
                    Rental newRental = new Rental(new RentalId(customer, inventories.get(0), LocalDateTime.now()), null); //creo un nuovo rent con rentalid e data di ritorno a null perchè lo sto noleggiando
                    rentalRepository.save(newRental);
                    return new ResponseEntity("You rented the film " + film.getTitle() + " from " + store.getStoreName(), HttpStatus.OK);
                }

            }




        }

        return new ResponseEntity<>("Nella scelta inserire Rent o Return!",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> countRentalsInDateRangeByStore(long storeId, LocalDate start, LocalDate end)
    {
        if(!storeRepository.existsById(storeId))
        {
            return new ResponseEntity<>("Store not existing!", HttpStatus.NOT_FOUND);
        }
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new ResourceNotFoundException("Store","Id", storeId));

        long rentalCount = rentalRepository.countRentalsInDateRangeOfStore(storeId,start,end);

        return new ResponseEntity<>(store.getStoreName() + " contains: " + rentalCount + " rents between " + start + " and " + end,HttpStatus.OK);
    }
}
