package io.ukhin.automechanics.repository;

import io.ukhin.automechanics.model.Shop;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ShopRepository extends CrudRepository<Shop, UUID> {
}