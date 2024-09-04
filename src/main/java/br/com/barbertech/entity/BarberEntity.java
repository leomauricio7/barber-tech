package br.com.barbertech.entity;

import br.com.barbertech.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity()
public class BarberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    // Relacionamento com Address (1:1)
    @OneToOne(mappedBy = "barber", cascade = CascadeType.ALL)
    @JsonManagedReference // Garante que esta coleção seja serializada
    private AddressEntity address;

}
