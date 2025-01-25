package it.BioShip.VideoStore25.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Genre
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long genreId;

    @Column(length = 30, unique = true, nullable = false)
    private String genreName;

    public Genre(String genreName)
    {
        this.genreName = genreName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return genreId == genre.genreId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(genreId);
    }
}
