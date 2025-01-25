package it.BioShip.VideoStore25.service;


import it.BioShip.VideoStore25.entity.Authority;
import it.BioShip.VideoStore25.exception.DefaultAuthorityException;
import it.BioShip.VideoStore25.exception.ResourceNotFoundException;
import it.BioShip.VideoStore25.repository.AuthorityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public ResponseEntity<?> addAuthority(String newAuthority) {
        if(authorityRepository.existsByAuthorityName(newAuthority))
            return new ResponseEntity<>("Authority already present", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(authorityRepository.save(new Authority(newAuthority)), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<?> switchVisibility(byte id) {
        Authority a = authorityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Authority","id", id));
        a.setVisible(!a.isVisible());
        return new ResponseEntity<>("Auhority updated", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> setNewDefault(byte id) {
        // trovare il ruolo di default

        Authority oldDefault = authorityRepository.findByDefaultAuthorityTrue();
        // settarlo default = false
        oldDefault.setDefaultAuthority(false);
        // trovare il ruolo che voglio diventi quello di default
        Authority newDefault = authorityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Authority","id", id));
        // settarlo default = true
        newDefault.setDefaultAuthority(true);
        // prova del nove: contare i flag default=true il conteggio DEVE essere uguale a 1
        long count = authorityRepository.countByDefaultAuthorityTrue();
        if(count !=1)
            throw new DefaultAuthorityException(count);

        return new ResponseEntity(newDefault.getAuthorityName() + " is the new default authority", HttpStatus.OK);
    }
}
