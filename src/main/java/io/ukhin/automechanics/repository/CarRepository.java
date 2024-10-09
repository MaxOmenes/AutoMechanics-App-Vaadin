package io.ukhin.automechanics.repository;

import io.ukhin.automechanics.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, String> {
}
