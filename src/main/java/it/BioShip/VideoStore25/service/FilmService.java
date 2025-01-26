package it.BioShip.VideoStore25.service;

import it.BioShip.VideoStore25.entity.*;
import it.BioShip.VideoStore25.exception.ResourceNotFoundException;
import it.BioShip.VideoStore25.payload.request.FilmRequest;
import it.BioShip.VideoStore25.payload.response.FilmMaxRentResponse;
import it.BioShip.VideoStore25.payload.response.FilmRentResponse;
import it.BioShip.VideoStore25.payload.response.FilmRentableResponse;
import it.BioShip.VideoStore25.payload.response.FilmResponse;
import it.BioShip.VideoStore25.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService
{
    private final FilmRepository filmRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;
    private final StoreRepository storeRepository;
    private final InventoryRepository inventoryRepository;
    private final CustomerRepository customerRepository;
    private final StaffRepository staffRepository;


    @Transactional
    public ResponseEntity<?> updateFilm(long filmId, FilmRequest filmRequest)
    {
        Film specificFilm = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film","Id",filmId));
        Language language = languageRepository.findById(filmRequest.getLanguageId()).orElseThrow(() -> new ResourceNotFoundException("Language","Id",filmRequest.getLanguageId()));
        Genre genre = genreRepository.findById(filmRequest.getGenreId()).orElseThrow(() -> new ResourceNotFoundException("Genre","Id",filmRequest.getGenreId()));

        specificFilm.setTitle(filmRequest.getTitle());
        specificFilm.setDescription(filmRequest.getDescription());
        specificFilm.setReleaseYear(filmRequest.getReleaseYear());
        specificFilm.setLanguageId(language);
        specificFilm.setGenreId(genre);

        return new ResponseEntity("Film Title changed to " + filmRequest.getTitle() + "\n"
                + "Film Description changed to " + filmRequest.getDescription() + "\n"
                + "Film Release Year changed to " + filmRequest.getReleaseYear() + "\n"
                + "Film Language  changed to " + language.getLanguageName() + "\n"
                + "Film Genre changed to " + genre.getGenreName(), HttpStatus.OK);
    }

    public ResponseEntity<?> findFilmsByLanguage(long languageId)
    {
        //PER RECUPERARE I FILM CON ID LINGUA 3: per esempio potevo cercare prima la lingua con ID 3, poi passare quella alla query jpql già fatta
        //Language language = languageRepository.findById(3L).orElseThrow(() -> new ResourceNotFoundException("Language", "id", 3));
        //List<Film> films = filmRepository.findByLanguageId(language);

        if(!languageRepository.existsById(languageId))
        {
            return new ResponseEntity<>("Language does not exist!",HttpStatus.NOT_FOUND);
        }

        Language language = languageRepository.findById(languageId).orElseThrow(() -> new ResourceNotFoundException("Language","Id",languageId));
        List<FilmResponse> filmsByLanguage = filmRepository.findByLanguageId(languageId);

        if(!filmsByLanguage.isEmpty())
        {
            return new ResponseEntity(filmsByLanguage,HttpStatus.OK);
        }
        return new ResponseEntity<>("No films found with " + language.getLanguageName() + " language", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> addFilmToStore(long storeId, long filmId)
    {
        if(!filmRepository.existsById(filmId))
        {
            return new ResponseEntity<>("Film not existing!",HttpStatus.NOT_FOUND);
        }
        if(!storeRepository.existsById(storeId))
        {
            return new ResponseEntity<>("Store not existing!",HttpStatus.NOT_FOUND);
        }

        Store store = storeRepository.findById(storeId).orElseThrow(() -> new ResourceNotFoundException("Store","Id",storeId));
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("Film","Id",filmId));

        Inventory inventory = new Inventory(store,film);
        inventoryRepository.save(inventory);

        return new ResponseEntity<>("Film: " + film.getTitle() + " added to Store: " + store.getStoreName(),HttpStatus.OK);
    }


    public ResponseEntity<?> findAllFilmsRentByOneCustomer(long customerId)
    {
        if(!customerRepository.existsById(customerId))
        {
            return  new ResponseEntity<>("Customer doesn't exist!",HttpStatus.NOT_FOUND);
        }
        //Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer","Id", customerId));

        List<FilmRentResponse> rentedFilmsList = filmRepository.findAllFilmsRentByOneCustomer(customerId);

        return new ResponseEntity<>(rentedFilmsList,HttpStatus.OK);
    }


    public ResponseEntity<?> findFilmWithMaxNumberOfRent()
    {
        List<FilmMaxRentResponse> filmsWithRents = filmRepository.filmsWithRentsCount(); //contiene i rent con il numero di rent, il primo è quello che ne ha di più
        long HighestRentCount = filmsWithRents.get(0).getNRents(); //dovrebbe essere 34 ora

        List<FilmMaxRentResponse> mostRentedFilms = new ArrayList<>(); //ci metto i film più visti, quelli che hanno 34 di rent

        for (int i = 0; i < filmsWithRents.size(); i++)
        {
            if(filmsWithRents.get(i).getNRents() == HighestRentCount) //se il film nella lista ha 34 lo metto
            {
                mostRentedFilms.add(filmsWithRents.get(i));
            }
            else //appena uno non ha 34 rompo il ciclo
            {
                break;
            }
        }


        return new ResponseEntity<>(mostRentedFilms,HttpStatus.OK);
    }

    public ResponseEntity<?> findFilmsByActors(Set<Long> actorsIdList)
    {
        for(Long staffId : actorsIdList)
        {
            if(!staffRepository.existsById(staffId))
            {
                return new ResponseEntity<>("Uno degli actors passati non esiste!",HttpStatus.BAD_REQUEST);
            }
        }
        List<FilmResponse> filmsWithSpecifiedActors = filmRepository.findFilmsByActorsSet(actorsIdList, actorsIdList.size());

        return new ResponseEntity<>(filmsWithSpecifiedActors,HttpStatus.OK);
    }

    public ResponseEntity<?> findRentableFilms(String title)
    {
        if(!filmRepository.existsByTitle(title))
        {
            return new ResponseEntity<>("Film: " + title + " doesn't exist!",HttpStatus.NOT_FOUND);
        }
        List<FilmRentableResponse> rentableFilms = filmRepository.findRentableFilms(title); //tutte le copie di DWARFS ALTER in tutti i negozi
        List<FilmRentableResponse> rentedFilms = filmRepository.findRentedFilms(title); //tutte le copie di DWARFS ALTER in ogni negozio con ritorno a null

        for(int i = 0; i < rentableFilms.size(); i++)
        {
            for (int j = 0; j < rentedFilms.size(); j++)
            {
                if(rentableFilms.get(i).getStoreName().equals(rentedFilms.get(j).getStoreName())) //"QUASIMODO" = "QUASIMODO"
                {
                    rentableFilms.get(i).setNAvailableCopies(rentableFilms.get(i).getNAvailableCopies() - rentedFilms.get(j).getNAvailableCopies()); //3 copie in stock nel film Dwarfs alter nel negozio Quasimodo e 1 rentata, sottraggo  1 da 3 e diventano 2 copie available
                }
            }
        }

        return new ResponseEntity<>(rentableFilms,HttpStatus.OK);

    }
}
