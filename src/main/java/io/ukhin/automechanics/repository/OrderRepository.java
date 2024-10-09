package io.ukhin.automechanics.repository;

import io.ukhin.automechanics.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {
    @Query("SELECT o FROM Order o where o.car.vin = (:vin) and o.date = (:date)")
    List<Order> findOrdersByVinAndDate(String vin, Date date);

    List<Order> findPartsById(UUID id);
}
