package br.com.barbertech.entity;

import br.com.barbertech.enums.GenderEnum;
import br.com.barbertech.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private UserRole role;  // Tipo de usuário: ADMIN, BARBEIRO, CLIENTE, etc.


    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private CompanyEntity company;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private BarberEntity barber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ClientEntity client;

}
