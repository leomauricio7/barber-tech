package br.com.barbertech.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity()
public class BarberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    // Relacionamento com Address (1:1)
    @OneToOne(mappedBy = "barber", cascade = CascadeType.ALL)
    @JsonManagedReference // Garante que esta coleção seja serializada
    private AddressEntity address;

}
