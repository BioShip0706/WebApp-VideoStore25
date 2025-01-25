package it.BioShip.VideoStore25.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilmMaxRentResponse
{
    private long filmId;

    private String title;

    private long nRents;

    public FilmMaxRentResponse(long filmId, String title, long nRents)
    {
        this.filmId = filmId;
        this.title = title;
        this.nRents = nRents;
    }
}
