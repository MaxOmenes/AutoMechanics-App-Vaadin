package io.ukhin.automechanics.repository;

import io.ukhin.automechanics.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findPartsById(UUID id);
    Order findOrderById(UUID id);
    Integer countOrderByStatus(Order.Status status);
}
