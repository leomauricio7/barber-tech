package br.com.barbertech.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class BarberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;
}
