package com.andima.billing.persistence.repository;

import com.andima.billing.persistence.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public interface ProductsRepository extends JpaRepository<Product, Integer> {
}
