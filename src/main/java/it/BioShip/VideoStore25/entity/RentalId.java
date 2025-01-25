package it.BioShip.VideoStore25.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RentalId
{
    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name = "inventory_id",nullable = false)
    private  Inventory inventoryId;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime rentalDate;

    public RentalId(Customer customerId, Inventory inventoryId, LocalDateTime rentalDate)
    {
        this.customerId = customerId;
        this.inventoryId = inventoryId;
        this.rentalDate = rentalDate;
    }

    public RentalId(Customer customerId, Inventory inventoryId)
    {
        this.customerId = customerId;
        this.inventoryId = inventoryId;
    }
}
