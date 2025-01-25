package it.BioShip.VideoStore25.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rental
{
    @EmbeddedId
    private RentalId rentalId;

    @PastOrPresent
    private LocalDateTime rentalReturn;

    public Rental(RentalId rentalId,LocalDateTime rentalReturn)
    {
        this.rentalId = rentalId;
        this.rentalReturn = rentalReturn;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return Objects.equals(rentalId, rental.rentalId) && Objects.equals(rentalReturn, rental.rentalReturn);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(rentalId, rentalReturn);
    }
}
