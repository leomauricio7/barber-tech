package br.com.barbertech.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity()
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    // Relacionamento com BarberEntity (1:1)
    @OneToOne(optional = true)
    @JoinColumn(name = "barber_id")
    @JsonBackReference // Evita serialização para quebrar o loop
    private BarberEntity barber;


    // Relacionamento com Barber (1:1)
    @OneToOne(optional = true)
    @JoinColumn(name = "client_id")
    @JsonBackReference // Evita serialização para quebrar o loop
    private ClientEntity client;
}
