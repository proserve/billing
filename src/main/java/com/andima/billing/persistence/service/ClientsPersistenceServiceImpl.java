package com.andima.billing.persistence.service;

import com.andima.billing.core.request.client.ClientDetail;
import com.andima.billing.core.service.ClientsPersistenceService;
import com.andima.billing.persistence.domain.Client;
import com.andima.billing.persistence.repository.ClientsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public class ClientsPersistenceServiceImpl implements ClientsPersistenceService {

    @Autowired
    ClientsRepository clientsRepository;

    @Override
    public ClientDetail createClient(ClientDetail detail) {
        Client client = Client.fromClientDetail(detail);
        Client savedClient = clientsRepository.save(client);
        ClientDetail clientDetail = savedClient.toClientDetail();
        return clientDetail;
    }

    @Override
    public List<ClientDetail> getAllClients() {
        List<Client> clients = clientsRepository.findAll();
        List<ClientDetail> clientDetails = new ArrayList<ClientDetail>();
        for (Client client : clients) {
            clientDetails.add(client.toClientDetail());
        }
        return clientDetails;
    }

    @Override
    public void deleteAllClient() {
        clientsRepository.deleteAll();
    }

    @Override
    public void delete(int key) {
        clientsRepository.delete(key);
    }

    @Override
    public ClientDetail update(ClientDetail detail) {
        Client client = clientsRepository.findOne(detail.getId());
        BeanUtils.copyProperties(detail, client);
        Client save = clientsRepository.save(client);
        return save.toClientDetail();
    }
}