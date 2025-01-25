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
public class Store
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long storeId;

    @Column(length = 60, unique = true, nullable = false)
    private String storeName;

    public Store(String storeName)
    {
        this.storeName = storeName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return storeId == store.storeId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(storeId);
    }
}
