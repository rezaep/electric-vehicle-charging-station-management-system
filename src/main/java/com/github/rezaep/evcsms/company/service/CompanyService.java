package com.github.rezaep.evcsms.company.service;

import com.github.rezaep.evcsms.company.domain.entity.Company;
import com.github.rezaep.evcsms.company.repository.CompanyRepository;
import com.github.rezaep.evcsms.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pl.exsio.nestedj.model.Tree;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;

    public Page<Company> getCompanies(Pageable pageable) {
        return repository.getCompanies(pageable);
    }

    public Company getCompany(long id) throws NotFoundException {
        return repository.getCompany(id);
    }

    public List<Company> getChildren(Company company) {
        return repository.getChildren(company);
    }

    public Set<Long> getCompanyAndChildrenIds(Company company) {
        Tree<Long, Company> tree = repository.getTree(company);

        return getCompanyAndChildrenIds(tree);
    }

    private Set<Long> getCompanyAndChildrenIds(Tree<Long, Company> tree) {
        Set<Long> ids = new HashSet<>();

        ids.add(tree.getNode().getId());

        for (Tree<Long, Company> childTree : tree.getChildren()) {
            Set<Long> childIds = this.getCompanyAndChildrenIds(childTree);
            ids.addAll(childIds);
        }

        return ids;
    }

    public Company createCompany(Long parentId, String name) throws NotFoundException {
        if (!ObjectUtils.isEmpty(parentId)) {
            Company parent = getCompany(parentId);
            return createChildCompany(parent, name);
        }

        return createParentCompany(name);
    }

    private Company createParentCompany(String name) {
        Company company = new Company(name);

        return repository.saveAsRootCompany(company);
    }

    private Company createChildCompany(Company parent, String name) {
        Company child = new Company(name);

        return repository.saveAsChildCompany(parent, child);
    }

    public Company updateCompany(long id, String name) throws NotFoundException {
        Company company = getCompany(id);
        company.setName(name);

        return repository.updateCompany(company);
    }

    public void deleteCompany(long id) throws NotFoundException {
        Company company = getCompany(id);

        repository.deleteCompany(company);
    }
}
