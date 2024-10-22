package io.ukhin.automechanics.service;

import io.ukhin.automechanics.model.Order;
import io.ukhin.automechanics.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.*;

@Service
public class OrdersService implements CrudListener<Order> {
    EntityManager em;
    OrderRepository orderRepository;

    @Autowired
public OrdersService(EntityManager em, OrderRepository orderRepository) {
        this.em = em;
        this.orderRepository = orderRepository;
    }

    @Override
    public Collection<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order add(Order domainObjectToAdd) {
        return orderRepository.save(domainObjectToAdd);
    }

    @Override
    public Order update(Order domainObjectToUpdate) {
        return orderRepository.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Order domainObjectToDelete) {
        orderRepository.delete(domainObjectToDelete);
    }

    public Order findById(UUID id) {
        return orderRepository.findOrderById(id);
    }

    public Collection<Order> findByFilters(String client, String car, Order.Status status, Date date) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Order.class);
        var root = query.from(Order.class);

        String[] clientParts = client.split(" ");
        String clientName = clientParts[0];
        String clientSurname = clientParts.length > 1 ? clientParts[1] : "";

        String[] carParts = car.split(" ");
        String carBrand = carParts[0];
        String carModel = carParts.length > 1 ? carParts[1] : "";

        var nameSurnameConcat = cb.concat(root.get("car").get("client").get("name"), " ");
        nameSurnameConcat = cb.concat(nameSurnameConcat, root.get("car").get("client").get("surname"));

        var brandModelConcat = cb.concat(root.get("car").get("brand"), " ");
        brandModelConcat = cb.concat(brandModelConcat, root.get("car").get("model"));

        ArrayList<Predicate> criteria = new ArrayList<>();
        criteria.add(cb.like(nameSurnameConcat, "%" + clientName + "%" + clientSurname));
        criteria.add(cb.like(brandModelConcat, "%" + carBrand + "%" + carModel));

        if (status != null) {
            criteria.add(cb.like(root.get("status"), "%" + status.name() + "%"));
        }

        if (date != null) {
            criteria.add(cb.equal(root.get("date"), date));
        }

        query.select(root).where(criteria.toArray(Predicate[]::new));
        return em.createQuery(query).getResultList();
    }

    public Map<Order.Status, Float> getStatistics() {
        var map = new HashMap<Order.Status, Float>();
        var count = new HashMap<Order.Status, Integer>();
        for (Order.Status status : Order.Status.values()) {
            count.put(status, orderRepository.countOrderByStatus(status));
        }
        var total = count.values().stream().mapToInt(Integer::intValue).sum();
        for (Order.Status status : Order.Status.values()) {
            map.put(status, (float) count.get(status) / total);
        }
        return map;
    }
}
