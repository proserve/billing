package com.andima.billing.persistence.service;

import com.andima.billing.PersistenceFixture;
import com.andima.billing.core.service.ProductsPersistenceService;
import com.andima.billing.config.JpaConfiguration;
import com.andima.billing.core.request.product.ProductDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
public class ProductsPersistenceServiceImplTest {

    @Autowired
    ProductsPersistenceService persistenceService;
    @Test
    public void testCreateProduct() throws Exception {
        ProductDetail testProduct = persistenceService.createProduct(PersistenceFixture.createProductDetail("testProduct"));
        assertNotNull(testProduct);
    }
}