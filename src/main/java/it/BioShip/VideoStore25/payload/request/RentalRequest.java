package it.BioShip.VideoStore25.payload.request;

import lombok.Getter;

@Getter
public class RentalRequest
{
    private long customerId;

    private long storeId;

    private long filmId;


}
