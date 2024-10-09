package io.ukhin.automechanics.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parts")
@ToString(exclude = "order")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private float price;

    @ManyToOne
    private Order order;


    @ManyToOne
    @JoinColumn(name = "delivery_note_id")
    private DeliveryNote deliveryNote;
}
