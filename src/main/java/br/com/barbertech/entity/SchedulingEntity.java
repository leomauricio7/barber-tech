package br.com.barbertech.entity;

import br.com.barbertech.enums.StatusSchedulingEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    @JsonManagedReference
    private BarberEntity barber;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonManagedReference
    private CompanyEntity company;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @JsonManagedReference
    private ServiceEntity serviceEntity;

    @OneToMany(mappedBy = "scheduling", cascade = CascadeType.ALL)
    private List<ReminderEntity> reminder;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    private Date date;

    @Enumerated(EnumType.STRING)
    private StatusSchedulingEnum status;

}
