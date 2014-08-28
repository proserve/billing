package com.andima.billing.persistence.service;

import com.andima.billing.core.request.address.AddressDetail;
import com.andima.billing.core.service.AddressesPersistenceService;
import com.andima.billing.persistence.domain.Address;
import com.andima.billing.persistence.repository.AddressesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public class AddressesPersistenceServiceImpl implements AddressesPersistenceService {

    @Autowired
    AddressesRepository addressesRepository;

    @Override
    public AddressDetail createAddress(AddressDetail detail) {
        Address address = Address.fromAddressDetail(detail);
        Address savedAddress = addressesRepository.save(address);
        AddressDetail addressDetail = savedAddress.toAddressDetail();
        return addressDetail;
    }

    @Override
    public List<AddressDetail> getAllAddresses() {
        List<Address> addresses = addressesRepository.findAll();
        System.out.println("ghiboub");
        List<AddressDetail> addressDetails = new ArrayList<AddressDetail>();
        if(addresses != null)
        for (Address address : addresses) {
            addressDetails.add(address.toAddressDetail());
        }
        return addressDetails;
    }

    @Override
    public void deleteAllAddress() {
        addressesRepository.deleteAll();
    }

    @Override
    public void delete(int key) {
        addressesRepository.delete(key);
    }

    @Override
    public AddressDetail update(AddressDetail detail) {
        Address address = addressesRepository.findOne(detail.getId());
        BeanUtils.copyProperties(detail, address);
        Address save = addressesRepository.save(address);
        return save.toAddressDetail();
    }
}