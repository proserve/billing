package com.andima.billing.persistence.domain;

import com.andima.billing.core.request.client.ClientDetail;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

/**
 * Created by GHIBOUB Khalid  on 20/08/2014.
 */
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Le nom du client  ne peut pas être vide")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Le prénom du client  ne peut pas être vide")
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    private Address  address;
    @NotBlank(message = "Le prénom du client  ne peut pas être vide")
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
        if(clientDetail.getAddress() != null)
        client.setAddress(Address.fromAddressDetail(clientDetail.getAddress()));
        return client;
    }
}
