package it.BioShip.VideoStore25.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class CustomerStoreResponse
{
    private String storeName;

    private long totalCustomers;

    public CustomerStoreResponse(String storeName, long totalCustomers)
    {
        this.storeName = storeName;
        this.totalCustomers = totalCustomers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerStoreResponse that = (CustomerStoreResponse) o;
        return totalCustomers == that.totalCustomers && Objects.equals(storeName, that.storeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeName, totalCustomers);
    }
}
