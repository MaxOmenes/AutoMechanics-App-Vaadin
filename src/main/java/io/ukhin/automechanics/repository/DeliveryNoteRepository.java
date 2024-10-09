package io.ukhin.automechanics.repository;

import io.ukhin.automechanics.model.DeliveryNote;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DeliveryNoteRepository extends CrudRepository<DeliveryNote, UUID> {
}