package io.ukhin.automechanics.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString(exclude = "car")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date date;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String description;
    private float price;
    @ManyToOne
    @JoinColumn(name = "car_vin")
    private Car car;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<Part> parts;

    public enum Status {
        ACCEPTED,
        IN_PROGRESS,
        DONE
    }
}
