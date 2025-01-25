package it.BioShip.VideoStore25.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Staff
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long staffId;

    @Column(length = 50, nullable = false)
    private String firstname;

    @Column(length = 50, nullable = false)
    private String lastname;

    private LocalDate dob;

    public Staff(String firstName, String lastName, LocalDate dob)
    {
        this.firstname = firstName;
        this.lastname = lastName;
        this.dob = dob;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return staffId == staff.staffId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(staffId);
    }
}
