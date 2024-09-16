package br.com.barbertech.entity;

import br.com.barbertech.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "barber_service",
            joinColumns = @JoinColumn(name = "barber_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<ServiceEntity> services;

    @ElementCollection
    private List<String> openingHours;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @OneToMany(mappedBy = "barber", cascade = CascadeType.ALL)
    private List<SchedulingEntity> scheduling;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
