package it.BioShip.VideoStore25.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long roleId;

    @Column(length = 30, unique = true, nullable = false)
    private String roleName;

}
