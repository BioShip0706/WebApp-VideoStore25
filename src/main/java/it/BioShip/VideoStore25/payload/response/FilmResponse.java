package it.BioShip.VideoStore25.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilmResponse
{
    private long filmId;

    private String title;

    private String description;

    private short releaseYear;

    private String languageName;

    public FilmResponse(long filmId, String title, String description, short releaseYear, String languageName)
    {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.languageName = languageName;
    }
}
