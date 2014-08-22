package com.andima.billing.client.domain;

import com.andima.billing.core.request.address.AddressDetail;
import org.springframework.beans.BeanUtils;

/**
 * Created by GHIBOUB Khalid  on 20/08/2014.
 */
public class Address {
    private int id;
    private String streetAddress;
    private String city;
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public AddressDetail toAddressDetail(){
        AddressDetail addressDetail = new AddressDetail();
        BeanUtils.copyProperties(this, addressDetail);
        return addressDetail;
    }

    public static Address fromAddressDetail(AddressDetail addressDetail){
        Address address = new Address();
        BeanUtils.copyProperties(addressDetail, address);
        return address;
    }

    @Override
    public String toString() {
        return state + " "+ city + " "+ streetAddress;
    }
}
