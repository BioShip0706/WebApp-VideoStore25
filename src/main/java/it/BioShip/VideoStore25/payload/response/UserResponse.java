package it.BioShip.VideoStore25.payload.response;


import it.BioShip.VideoStore25.entity.Customer;
import it.BioShip.VideoStore25.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserResponse {

    private int id;
    private String username;
    private LocalDateTime createdAt;
    private Customer customerId;


    static public UserResponse fromUserDetailsToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getCreatedAt(),
                user.getCustomerId()
        );
    }

}
