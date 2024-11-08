package br.com.barbertech.entity;

import br.com.barbertech.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String openingHours;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<BarberEntity> barbers;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<SchedulingEntity> scheduling;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ServiceEntity> services;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonManagedReference
    private AddressEntity address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;

}
