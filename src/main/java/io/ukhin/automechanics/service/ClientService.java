package io.ukhin.automechanics.service;

import io.ukhin.automechanics.model.Client;
import io.ukhin.automechanics.repository.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;
@Service
public class ClientService implements CrudListener<Client> {
    EntityManager em;
    ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, EntityManager em) {
        this.clientRepository = clientRepository;
        this.em = em;
    }

    @Override
    public Collection<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client add(Client domainObjectToAdd) {
        return clientRepository.save(domainObjectToAdd);
    }

    @Override
    public Client update(Client domainObjectToUpdate) {
        return clientRepository.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Client domainObjectToDelete) {
        clientRepository.delete(domainObjectToDelete);
    }

    public List<Client> findByFilters(String name, String surname, String patronymic, String phone) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Client.class);
        var root = query.from(Client.class);
        query.select(root).where(
                cb.like(root.get("name"), "%" + name + "%"),
                cb.like(root.get("surname"), "%" + surname + "%"),
                cb.like(root.get("patronymic"), "%" + patronymic + "%"),
                cb.like(root.get("phone"), "%" + phone + "%")
        );
        return em.createQuery(query).getResultList();
    }
}
