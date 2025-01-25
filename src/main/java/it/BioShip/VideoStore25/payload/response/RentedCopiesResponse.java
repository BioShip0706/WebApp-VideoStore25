package it.BioShip.VideoStore25.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class RentedCopiesResponse
{
    private Long customerId;
    private Long inventoryId;
    private LocalDateTime rentalDate;
    private LocalDateTime rentalReturn;

    public RentedCopiesResponse(Long customerId, Long inventoryId, LocalDateTime rentalDate, LocalDateTime rentalReturn)
    {
        this.customerId = customerId;
        this.inventoryId = inventoryId;
        this.rentalDate = rentalDate;
        this.rentalReturn = rentalReturn;
    }
}
