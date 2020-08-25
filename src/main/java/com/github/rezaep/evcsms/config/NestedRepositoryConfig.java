package com.github.rezaep.evcsms.config;

import com.github.rezaep.evcsms.company.domain.entity.Company;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.exsio.nestedj.NestedNodeRepository;
import pl.exsio.nestedj.config.jpa.JpaNestedNodeRepositoryConfiguration;
import pl.exsio.nestedj.config.jpa.factory.JpaNestedNodeRepositoryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class NestedRepositoryConfig {
    @PersistenceContext
    EntityManager entityManager;

    @Bean
    public NestedNodeRepository<Long, Company> repository() {
        JpaNestedNodeRepositoryConfiguration<Long, Company> configuration = new JpaNestedNodeRepositoryConfiguration<>(
                entityManager, Company.class, Long.class);

        return JpaNestedNodeRepositoryFactory.create(configuration);
    }
}
