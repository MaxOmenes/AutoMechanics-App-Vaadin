package io.ukhin.automechanics.service;

import io.ukhin.automechanics.model.Car;
import io.ukhin.automechanics.repository.CarRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@Service
public class CarService implements CrudListener<Car> {
    EntityManager em;
    CarRepository carRepository;

    @Autowired
    public CarService(EntityManager em, CarRepository carRepository) {
        this.em = em;
        this.carRepository = carRepository;
    }

    @Override
    public Collection<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car add(Car domainObjectToAdd) {
        return carRepository.save(domainObjectToAdd);
    }

    @Override
    public Car update(Car domainObjectToUpdate) {
        return carRepository.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Car domainObjectToDelete) {
        carRepository.delete(domainObjectToDelete);
    }

    public List<Car> findByFilters(String brand, String model, String vin, String number) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Car.class);
        var root = query.from(Car.class);
        query.select(root).where(
                cb.like(root.get("brand"), "%" + brand + "%"),
                cb.like(root.get("model"), "%" + model + "%"),
                cb.like(root.get("vin"), "%" + vin + "%"),
                cb.like(root.get("number"), "%" + number + "%")
        );
        return em.createQuery(query).getResultList();
    }
}
