package com.andima.billing.persistence.repository;

import com.andima.billing.persistence.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public interface AddressesRepository extends JpaRepository<Address, Integer> {
}
