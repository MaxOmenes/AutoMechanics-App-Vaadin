package io.ukhin.automechanics.repository;

import io.ukhin.automechanics.model.Car;
import io.ukhin.automechanics.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID>, JpaSpecificationExecutor<Client> {
    @Query("SELECT c FROM Car c where c.client.id = (:id)")
    List<Car> findCarsByClientId(@Param("id") Long id);

    List<Client> findByNameContainingIgnoreCase(String name);

}
