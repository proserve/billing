package com.andima.billing.client.domain;

import com.andima.billing.core.request.client.ClientDetail;
import org.springframework.beans.BeanUtils;

/**
 * Created by GHIBOUB Khalid  on 20/08/2014.
 */
public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private Address address;
    private String NCF;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getNCF() {
        return NCF;
    }

    public void setNCF(String NCF) {
        this.NCF = NCF;
    }

    public ClientDetail toClientDetail(){
        ClientDetail clientDetail = new ClientDetail();
        BeanUtils.copyProperties(this, clientDetail);
        if (address != null)
        clientDetail.setAddress(address.toAddressDetail());
        return clientDetail;
    }

    public static Client fromClientDetail(ClientDetail clientDetail){
        Client client = new Client();
        BeanUtils.copyProperties(clientDetail, client);
        if (clientDetail.getAddress() != null)
        client.setAddress(Address.fromAddressDetail(clientDetail.getAddress()));
        return client;
    }
}
