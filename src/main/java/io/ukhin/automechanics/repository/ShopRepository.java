package io.ukhin.automechanics.repository;

import io.ukhin.automechanics.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShopRepository extends JpaRepository<Shop, UUID> {
}