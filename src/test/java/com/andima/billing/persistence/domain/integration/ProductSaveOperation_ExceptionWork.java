package com.andima.billing.persistence.domain.integration;

import com.andima.billing.PersistenceFixture;
import com.andima.billing.config.JpaConfiguration;
import com.andima.billing.persistence.domain.Product;
import com.andima.billing.persistence.repository.ProductsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
public class ProductSaveOperation_ExceptionWork {
    public static final String PRODUCT_1 = "product1";
    @Autowired
    ProductsRepository productsRepository;

    @After
    public void tearDown() throws Exception {
       productsRepository.deleteAll();
    }

    @Test
    public void validationExceptionTest()   {
        try {
            Product product1 = PersistenceFixture.createProduct(PRODUCT_1);
            productsRepository.save(product1);

            Product otherProduct = PersistenceFixture.createProduct(PRODUCT_1);
            productsRepository.save(otherProduct);
        }catch (Exception e){

            System.out.println(e.getCause()+  " ghioub");

        }

    }
}
