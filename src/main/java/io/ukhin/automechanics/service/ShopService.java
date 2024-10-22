package io.ukhin.automechanics.service;

import io.ukhin.automechanics.model.Shop;
import io.ukhin.automechanics.repository.ShopRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@Service
public class ShopService implements CrudListener<Shop> {
    EntityManager em;
    ShopRepository shopRepository;

    @Autowired
    public ShopService(EntityManager em, ShopRepository shopRepository) {
        this.em = em;
        this.shopRepository = shopRepository;
    }

    @Override
    public Collection<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Override
    public Shop add(Shop domainObjectToAdd) {
        return shopRepository.save(domainObjectToAdd);
    }

    @Override
    public Shop update(Shop domainObjectToUpdate) {
        return shopRepository.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Shop domainObjectToDelete) {
        shopRepository.delete(domainObjectToDelete);
    }

    public List<Shop> findByFilters(String name, String address, String phone) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Shop.class);
        var root = query.from(Shop.class);
        query.select(root).where(
                cb.like(root.get("name"), "%" + name + "%"),
                cb.like(root.get("address"), "%" + address + "%"),
                cb.like(root.get("phone"), "%" + phone + "%")
        );

        return em.createQuery(query).getResultList();
    }
}
