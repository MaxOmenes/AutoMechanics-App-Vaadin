package io.ukhin.automechanics.repository;

import io.ukhin.automechanics.model.Worker;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WorkerRepository extends CrudRepository<Worker, UUID> {
}