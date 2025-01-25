package it.BioShip.VideoStore25.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class FilmRentResponse
{
    private long filmId;

    private String title;

    private String storeName;

    public FilmRentResponse(long filmId, String title, String storeName)
    {
        this.filmId = filmId;
        this.title = title;
        this.storeName = storeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmRentResponse that = (FilmRentResponse) o;
        return filmId == that.filmId && Objects.equals(title, that.title) && Objects.equals(storeName, that.storeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, title, storeName);
    }
}
