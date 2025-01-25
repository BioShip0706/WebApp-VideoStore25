package it.BioShip.VideoStore25.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilmRentableResponse
{
    private String title;

    private String storeName;

    private long nStockCopies; //in possesso del negozio

    private long nAvailableCopies; //copie disponibili (non noleggiate)


    public FilmRentableResponse(String title, String storeName, long nStockCopies)
    {
        this.title = title;
        this.storeName = storeName;
        this.nStockCopies = nStockCopies;
        this.nAvailableCopies = nStockCopies;
    }

}
