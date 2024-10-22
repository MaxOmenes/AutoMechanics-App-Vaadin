package io.ukhin.automechanics.repository;

import io.ukhin.automechanics.model.DeliveryNote;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface DeliveryNoteRepository extends JpaRepository<DeliveryNote, UUID> {
}