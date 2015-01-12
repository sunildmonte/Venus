package org.venus.infra.config.database;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.venus.infra.config.properties.AppProperties;

/**
 * The datasource and other database/JPA related beans used in the running application.
 * 
 *  @see TestDatabaseConfig for the corresponding config used when running tests.
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);

    @Autowired
    private AppProperties appProperties;

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup jndi = new JndiDataSourceLookup();
        DataSource ds = jndi.getDataSource(appProperties.datasourceJndiName());
        LOG.debug("dataSource: {}", ds);
        return ds;
    }

//    @Bean
//    public DataSource appDataSource() {
//        JndiDataSourceLookup jndi = new JndiDataSourceLookup();
//        DataSource ds = jndi.getDataSource(appProperties.datasourceJndiName());
//        LOG.debug("appDataSource: {}", ds);
//        return ds;
//    }
//    
//    @Bean
//    public AbstractJpaVendorAdapter jpaVendorAdapter() {
//        AbstractJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
//        jpaAdapter.setGenerateDdl(false);
//        jpaAdapter.setShowSql(true);
//        jpaAdapter.setDatabase(Database.valueOf(appProperties.databaseVendor())); 
//        LOG.debug("jpaVendorAdapter: {}", jpaAdapter);
//        return jpaAdapter;
//    }
//    
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(appDataSource());
//        emf.setJpaVendorAdapter(jpaVendorAdapter());
//        emf.setValidationMode(ValidationMode.CALLBACK);
//        emf.setPackagesToScan("org.venus"); //TODO can this be optimized to reduce the scanning?
//        
//        Map<String, String> jpaProperties = new HashMap<String, String>();
////        jpaProperties.put("hibernate.cache.use_second_level_cache", "true");
////        jpaProperties.put("hibernate.cache.use_query_cache", "true");
////        jpaProperties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.infinispan.InfinispanRegionFactory");
////        jpaProperties.put("hibernate.cache.default_cache_concurrency_strategy", "nonstrict-read-write");
////        emf.setJpaPropertyMap(jpaProperties);
//        
//        LOG.debug("entityManagerFactory: {}", emf);
//        return emf;
//    }
//    
//    @Bean
//    public JpaTransactionManager transactionManager() {
//        JpaTransactionManager tm = new JpaTransactionManager();
//        tm.setEntityManagerFactory(entityManagerFactory().getObject());
//        LOG.debug("transactionManager: {}", tm);
//        return tm;
//    }
}

