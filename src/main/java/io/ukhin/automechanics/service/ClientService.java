package io.ukhin.automechanics.service;

import io.ukhin.automechanics.model.Client;
import io.ukhin.automechanics.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;
@Service
public class ClientService implements CrudListener<Client> {
    ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
}
