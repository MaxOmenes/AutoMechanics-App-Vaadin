package io.ukhin.automechanics.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@ToString(exclude = "client")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    private String vin;
    private String brand;
    private String model;
    private Integer year;
    private String number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private List<Order> orders;

}
