package io.ukhin.automechanics.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services")
@ToString(exclude = "workers")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private float price;

    @ManyToMany
    @JoinTable(
            name = "worker_services",
            inverseJoinColumns = @JoinColumn(name = "worker_id"),
            joinColumns = @JoinColumn(name = "service_id")
    )
    private List<Worker> workers;
}
