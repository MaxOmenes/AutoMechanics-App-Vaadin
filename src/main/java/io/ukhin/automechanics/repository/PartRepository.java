package io.ukhin.automechanics.repository;

import io.ukhin.automechanics.model.Part;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PartRepository extends CrudRepository<Part, UUID> {
    @Query("SELECT p FROM Part p where p.order.id = (:id)")
    List<Part> findByOrder(UUID id);
}
