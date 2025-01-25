package it.BioShip.VideoStore25.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
// @Table(name="authority")
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private byte id;

    @Column(length = 30, nullable = false, unique = true)
    private String authorityName; // authority_name


    private boolean visible = true;

    private boolean defaultAuthority = false;

    public Authority(String authorityName) {
        this.authorityName = authorityName;
    }


}
