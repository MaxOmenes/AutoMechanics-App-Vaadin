package io.ukhin.automechanics.repository;

import io.ukhin.automechanics.model.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceRepository extends CrudRepository<Service, UUID> {
}