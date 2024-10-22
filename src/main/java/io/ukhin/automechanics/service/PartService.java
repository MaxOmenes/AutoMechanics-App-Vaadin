package io.ukhin.automechanics.service;

import io.ukhin.automechanics.model.Part;
import io.ukhin.automechanics.repository.PartRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class PartService implements CrudListener<Part> {
    EntityManager em;
    PartRepository partRepository;

    @Autowired
    public PartService(EntityManager em, PartRepository partRepository) {
        this.em = em;
        this.partRepository = partRepository;
    }

    @Override
    public Collection<Part> findAll() {
        return partRepository.findAll();
    }

    @Override
    public Part add(Part domainObjectToAdd) {
        return partRepository.save(domainObjectToAdd);
    }

    @Override
    public Part update(Part domainObjectToUpdate) {
        return partRepository.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Part domainObjectToDelete) {
        partRepository.delete(domainObjectToDelete);
    }

    public List<Part> findByFilters(String name, String sign, Double price) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Part.class);
        var root = query.from(Part.class);

        if (sign == null || sign.isEmpty()) {
            query.select(root).where(
                    cb.like(root.get("name"), "%" + name + "%")
            );
            return em.createQuery(query).getResultList();
        }

        query.select(root).where(
                cb.like(root.get("name"), "%" + name + "%"),
                (sign.equals(">") ? cb.greaterThan(root.get("price"), price) : cb.lessThan(root.get("price"), price))
        );
        return em.createQuery(query).getResultList();
    }

    public List<Part> findByOrder(UUID id) {
        return partRepository.findByOrder(id);
    }
}
