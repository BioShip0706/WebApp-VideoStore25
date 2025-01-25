package it.BioShip.VideoStore25.service;

import it.BioShip.VideoStore25.entity.Store;
import it.BioShip.VideoStore25.payload.response.CustomerStoreResponse;
import it.BioShip.VideoStore25.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StoreService
{
    private final FilmRepository filmRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;
    private final StoreRepository storeRepository;
    private final InventoryRepository inventoryRepository;

    public ResponseEntity<?> countCustomersByStore(String storeName)
    {
        //Store store = storeRepository.findByStoreName(storeName);

        if(!storeRepository.existsByStoreName(storeName))
        {
            return new ResponseEntity<>("Store not existing!",HttpStatus.NOT_FOUND);
        }

        CustomerStoreResponse customerStoreResponse =  storeRepository.countCustomersByStoreName(storeName);
        return new ResponseEntity<>(customerStoreResponse, HttpStatus.OK);
    }
}
