package br.com.barbertech.entity;

import br.com.barbertech.enums.StatusSchedulingEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SchedulingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private BarberEntity barber;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity serviceEntity;

    @OneToMany(mappedBy = "scheduling", cascade = CascadeType.ALL)
    private List<ReminderEntity> reminder;

    @Column(nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    private StatusSchedulingEnum status;




}
