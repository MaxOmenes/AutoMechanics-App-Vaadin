package io.ukhin.automechanics.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workers")
@ToString(exclude = "services")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String surname;
    private String patronymic;
    private String phone;
    private Date birthday;
    private String job;
    private float salary;
    private float experience;
    private float experienceBonus;
    private String operatingMode;

    @ManyToMany
    @JoinTable(
            name = "worker_services",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;
}
