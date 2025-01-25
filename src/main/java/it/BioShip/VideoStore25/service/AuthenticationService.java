package it.BioShip.VideoStore25.service;


import it.BioShip.VideoStore25.entity.Authority;
import it.BioShip.VideoStore25.entity.Customer;
import it.BioShip.VideoStore25.entity.User;
import it.BioShip.VideoStore25.payload.request.SigninRequest;
import it.BioShip.VideoStore25.payload.request.SignupRequest;
import it.BioShip.VideoStore25.payload.response.AuthenticationResponse;
import it.BioShip.VideoStore25.repository.AuthorityRepository;
import it.BioShip.VideoStore25.repository.UserRepository;
import it.BioShip.VideoStore25.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> signup(SignupRequest request)
    {

        if(userRepository.existsByUsername(request.getUsername()))
        {
            return new ResponseEntity<String>("Username already in use", HttpStatus.BAD_REQUEST);
        }

        Authority a = authorityRepository.findByDefaultAuthorityTrue(); //trova ROLE_MEMBER

        Customer newCustomer = new Customer(request.getName(),request.getSurname(),request.getEmail());

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .customerId(newCustomer)
                .authorities(Collections.singleton(a))
                .build();

        userRepository.save(user);

        log.info("### "+user.toString());

        return new ResponseEntity<String>("User successfully registered",HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<?> signin(SigninRequest request)
    {
        if(!userRepository.existsByUsername(request.getUsername()))
        {
            return new ResponseEntity<String>("Bad credentials or User not existing", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new BadCredentialsException("Bad credentials"));


        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
        {
            //throw new BadCredentialsException("Bad credentials");
            return new ResponseEntity<String>("Bad credentials or User not existing", HttpStatus.BAD_REQUEST);
        }

        /*boolean isGuest = ArrayUtils.contains(authorities(user.getAuthorities()), "ROLE_GUEST");
        if(!user.isEnabled() && isGuest)
        {
            return new ResponseEntity("Please check your email and activate your  account",HttpStatus.UNAUTHORIZED);
        }*/




        String jwtToken = jwtService.generateToken(user, user.getId());
        return new ResponseEntity(AuthenticationResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .customerId(user.getCustomerId().getCustomerId())
                .authorities(authorities(user.getAuthorities()))
                .token(jwtToken)
                .build(),
                HttpStatus.OK);
    }

    private String[] authorities(Collection<? extends GrantedAuthority> auths){
        return auths.stream().map(a -> a.getAuthority())
                .toArray(String[]::new);
    }

}
