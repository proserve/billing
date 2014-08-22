package com.andima.billing.core.request.client;

import com.andima.billing.core.request.address.AddressDetail;

/**
 * Created by GHIBOUB Khalid  on 20/08/2014.
 */
public class ClientDetail {
    private int id;
    private String firstName;
    private String lastName;
    private AddressDetail address;
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

    public AddressDetail getAddress() {
        return address;
    }

    public void setAddress(AddressDetail address) {
        this.address = address;
    }

    public String getNCF() {
        return NCF;
    }

    public void setNCF(String NCF) {
        this.NCF = NCF;
    }
}
