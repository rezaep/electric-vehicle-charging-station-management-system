package com.github.rezaep.evcsms.company.model;

import com.github.rezaep.evcsms.company.domain.entity.Company;

public class CompanyTestDataBuilder {
    private long id;
    private String name;
    private Long parentId;

    private CompanyTestDataBuilder() {

    }

    public static CompanyTestDataBuilder aCompany() {
        return new CompanyTestDataBuilder();
    }

    public static CompanyTestDataBuilder aValidCompany() {
        return aValidParentCompany();
    }

    public static CompanyTestDataBuilder aValidParentCompany() {
        return new CompanyTestDataBuilder()
                .withId(1)
                .withName("company-1")
                .withParentId(null);
    }

    public static CompanyTestDataBuilder aValidChildCompany() {
        return new CompanyTestDataBuilder()
                .withId(2)
                .withName("company-2")
                .withParentId(1L);
    }

    public CompanyTestDataBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public CompanyTestDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CompanyTestDataBuilder withParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public CompanyTestDataBuilder but() {
        return CompanyTestDataBuilder
                .aCompany()
                .withId(id)
                .withName(name)
                .withParentId(parentId);
    }

    public Company build() {
        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setParentId(parentId);

        return company;
    }
}
