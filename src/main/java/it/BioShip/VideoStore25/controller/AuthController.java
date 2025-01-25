package it.BioShip.VideoStore25.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import it.BioShip.VideoStore25.payload.request.SigninRequest;
import it.BioShip.VideoStore25.payload.request.SignupRequest;
import it.BioShip.VideoStore25.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;



    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest signupRequest)
    {
        return authenticationService.signup(signupRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid SigninRequest signinRequest) throws Exception
    {
        return authenticationService.signin(signinRequest);
    }




}
