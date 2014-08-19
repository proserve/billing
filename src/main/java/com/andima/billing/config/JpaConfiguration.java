package com.andima.billing.config;

import com.andima.billing.core.service.ProductsPersistenceService;
import com.andima.billing.persistence.service.ProductsPersistenceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
@PropertySource("classpath:application.properties")
@Configuration
@EnableJpaRepositories(basePackages = "com.andima.billing.persistence.repository")
public class JpaConfiguration {
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

    @Resource
    private Environment environment;

    @Bean
    public DataSource dataSource() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setPassword(environment.getProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        dataSource.setUsername(environment.getProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setDriverClassName(environment.getProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(environment.getProperty(PROPERTY_NAME_DATABASE_URL));
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws SQLException {
        HibernateJpaVendorAdapter vendorAdapter = getHibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factoryBean = getLocalContainerEntityManagerFactoryBean(vendorAdapter);
        return factoryBean.getObject();
    }

    private LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean
            (HibernateJpaVendorAdapter vendorAdapter) throws SQLException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.andima.billing.persistence.domain");
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    private HibernateJpaVendorAdapter getHibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);
        return vendorAdapter;
    }

    @Bean
    public EntityManager entityManager() throws SQLException {
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() throws SQLException {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public ProductsPersistenceService productsPersistenceService(){
        return new ProductsPersistenceServiceImpl();
    }
}