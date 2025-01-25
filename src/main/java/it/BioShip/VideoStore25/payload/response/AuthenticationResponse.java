package it.BioShip.VideoStore25.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private int id;
    private String username;
    private long customerId;
    private String[] authorities;
    private String token;

}
