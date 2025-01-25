package it.BioShip.VideoStore25.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class FilmRequest
{
    @NotBlank @Length(min = 1, max = 100)
    private String title;

    @NotBlank
    private String description;

    @Min(1)
    private short releaseYear;

    @Min(1)
    private long languageId;

    @Min(1)
    private long genreId;
}
