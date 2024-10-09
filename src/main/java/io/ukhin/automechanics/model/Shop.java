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
@Table(name = "shops")
@ToString
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String address;
    private String phone;

    @OneToMany(mappedBy = "shop")
    private List<DeliveryNote> deliveryNotes;
}
