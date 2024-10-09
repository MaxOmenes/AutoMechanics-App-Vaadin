package io.ukhin.automechanics.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "delivery_notes")
public class DeliveryNote {
    @Id
    private UUID id;
    private Date date;

    @ManyToOne
    private Shop shop;

    @OneToMany
    private List<Part> part;
}
