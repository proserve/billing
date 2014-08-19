package com.andima.billing.core.useCases.transactions;

import com.andima.billing.PersistenceFixture;
import com.andima.billing.config.JpaConfiguration;
import com.andima.billing.core.request.product.ProductDetail;
import com.andima.billing.core.request.product.response.CreateProductResponse;
import com.andima.billing.core.service.ProductsPersistenceService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
public class AddProductTransactionTest {
    @Autowired
    ProductsPersistenceService productsPersistenceService;

    @After
    public void tearDown() throws Exception {
        productsPersistenceService.deleteAllProduct();
    }

    @Test
    public void testExecute() throws Exception {
        ProductDetail detail = PersistenceFixture.createProductDetail("test");
        AddProductTransaction transaction = new AddProductTransaction(detail, productsPersistenceService);
        CreateProductResponse response = transaction.execute();
        assertNotNull(response.getDetail().getNumber()>0);
        assertTrue(response.getKey()>0);
    }
}