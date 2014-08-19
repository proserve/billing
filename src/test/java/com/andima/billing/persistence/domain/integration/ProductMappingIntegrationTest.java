package com.andima.billing.persistence.domain.integration;

import com.andima.billing.config.JpaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;

import static com.andima.billing.persistence.domain.fixture.JpaAssertion.assertThatTableExiste;
import static com.andima.billing.persistence.domain.fixture.JpaAssertion.assertThatTableHasColumn;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
public class ProductMappingIntegrationTest {
    @Autowired
    EntityManager entityManager;

    @Test
    public void thatProductCustomMappingWork() throws Exception {
        assertThatTableExiste(entityManager, "Product");
        assertThatTableHasColumn(entityManager, "Product", "id");
        assertThatTableHasColumn(entityManager, "Product", "name");
        assertThatTableHasColumn(entityManager, "Product", "metric_unit");
        assertThatTableHasColumn(entityManager, "Product", "unite_price");
    }
}
