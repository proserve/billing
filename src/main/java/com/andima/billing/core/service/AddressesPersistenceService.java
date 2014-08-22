package com.andima.billing.core.service;


import com.andima.billing.core.request.address.AddressDetail;

import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public interface AddressesPersistenceService {
    public AddressDetail createAddress(AddressDetail detail);
    public List<AddressDetail> getAllAddresses();
    public void deleteAllAddress();
    public void delete(int key);
    public AddressDetail update(AddressDetail detail);
}
