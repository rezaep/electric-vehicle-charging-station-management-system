package com.github.rezaep.evcsms.company.repository;

import com.github.rezaep.evcsms.company.domain.entity.Company;
import com.github.rezaep.evcsms.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.exsio.nestedj.NestedNodeRepository;
import pl.exsio.nestedj.model.Tree;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {
    private final NestedNodeRepository<Long, Company> repository;

    @PersistenceContext
    private EntityManager em;

    public Page<Company> getCompanies(Pageable pageable) {
        return Page.empty();
    }

    public Company getCompany(long id) throws NotFoundException {
        return Optional.ofNullable(em.find(Company.class, id))
                .orElseThrow(NotFoundException::new);
    }

    public List<Company> getChildren(Company company) {
        return repository.getChildren(company);
    }

    public Tree<Long, Company> getTree(Company company) {
        return repository.getTree(company);
    }

    @Transactional
    public Company saveAsRootCompany(Company company) {
        repository.insertAsFirstRoot(company);

        return company;
    }

    @Transactional
    public Company saveAsChildCompany(Company parent, Company child) {
        repository.insertAsLastChildOf(child, parent);

        return child;
    }

    @Transactional
    public Company updateCompany(Company company) {
        em.refresh(company);

        return company;
    }

    @Transactional
    public void deleteCompany(Company company) {
        repository.removeSubtree(company);
    }
}
