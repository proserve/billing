package com.andima.billing.core.service;

import com.andima.billing.core.request.client.ClientDetail;

import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public interface ClientsPersistenceService {
    public ClientDetail createClient(ClientDetail detail);
    public List<ClientDetail> getAllClients();
    public void deleteAllClient();
    public void delete(int key);
    public ClientDetail update(ClientDetail detail);
}
