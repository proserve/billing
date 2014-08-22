package com.andima.billing.persistence.domain;

import com.andima.billing.core.request.address.AddressDetail;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
