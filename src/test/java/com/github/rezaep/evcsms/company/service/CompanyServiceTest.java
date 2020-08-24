package com.github.rezaep.evcsms.company.service;

import com.github.rezaep.evcsms.company.domain.entity.Company;
import com.github.rezaep.evcsms.company.model.CompanyTestDataBuilder;
import com.github.rezaep.evcsms.company.repository.CompanyRepository;
import com.github.rezaep.evcsms.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.exsio.nestedj.model.InMemoryTree;
import pl.exsio.nestedj.model.Tree;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyRepository repository;
    @InjectMocks
    private CompanyService service;

    @Test
    public void shouldGetCompanyReturnCompanyWhenIdIsValid() throws NotFoundException {
        Company expectedCompany = CompanyTestDataBuilder.aCompany()
                .withId(1)
                .withName("company-1")
                .build();

        when(repository.getCompany(1)).thenReturn(expectedCompany);

        Company actualCompany = service.getCompany(1);

        assertThat(actualCompany.getId()).isEqualTo(expectedCompany.getId());
        assertThat(actualCompany.getName()).isEqualTo(expectedCompany.getName());
        assertThat(actualCompany.getStations()).isNullOrEmpty();
    }

    @Test
    public void shouldGetCompanyThrowNotFoundExceptionWhenCompanyIdIsNotValid() throws NotFoundException {
        when(repository.getCompany(1)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> service.getCompany(1));
    }

    @Test
    public void shouldGetChildrenReturnListOfChildrenCompanyWhenIdIsValid() throws NotFoundException {
        Company parentCompany = CompanyTestDataBuilder.aCompany()
                .withId(1)
                .withName("parent")
                .build();

        List<Company> expectedChildren = List.of(
                CompanyTestDataBuilder.aCompany().withId(2).withName("child-1").build(),
                CompanyTestDataBuilder.aCompany().withId(3).withName("child-2").build(),
                CompanyTestDataBuilder.aCompany().withId(4).withName("child-3").build()
        );

        when(repository.getChildren(parentCompany)).thenReturn(expectedChildren);

        List<Company> actualChildren = service.getChildren(parentCompany);

        assertThat(actualChildren).hasSize(3)
                .containsExactlyInAnyOrderElementsOf(expectedChildren);
    }

    @Test
    public void shouldGetCompanyAndChildrenIds() {
        Company parent = CompanyTestDataBuilder.aValidParentCompany()
                .withId(1)
                .build();

        Tree<Long, Company> tree = setupTreeForGetCompanyIdsTest(parent);

        when(repository.getTree(parent)).thenReturn(tree);

        Set<Long> expectedIds = Set.of(1L, 2L, 3L, 4L);
        Set<Long> actualIds = service.getCompanyAndChildrenIds(parent);

        assertThat(actualIds).hasSize(4)
                .containsExactlyInAnyOrderElementsOf(expectedIds);

    }

    private Tree<Long, Company> setupTreeForGetCompanyIdsTest(Company parent) {
        Company child1 = CompanyTestDataBuilder.aValidChildCompany()
                .withId(2)
                .build();
        Company child2 = CompanyTestDataBuilder.aValidChildCompany()
                .withId(3)
                .build();
        Company child3 = CompanyTestDataBuilder.aValidChildCompany()
                .withId(4)
                .build();

        InMemoryTree<Long, Company> tree = new InMemoryTree<>(parent);

        tree.setChildren(List.of(
                new InMemoryTree<>(child1),
                new InMemoryTree<>(child2),
                new InMemoryTree<>(child3)
        ));

        return tree;
    }

    @Test
    public void shouldCreateParentCompanyWhenParentIdIsNull() throws NotFoundException {
        Company expectedCompany = CompanyTestDataBuilder.aValidParentCompany()
                .withName("parent")
                .build();

        when(repository.saveAsRootCompany(any())).thenReturn(expectedCompany);

        Company actualCompany = service.createCompany(null, "parent");

        assertThat(actualCompany.getName()).isEqualTo(expectedCompany.getName());
        assertThat(actualCompany.getParentId()).isNull();
    }

    @Test
    public void shouldCreateChildCompanyWhenParentIdIsNotNullAndIsValid() throws NotFoundException {
        Company parentCompany = CompanyTestDataBuilder.aValidParentCompany()
                .build();

        Company expectedChildCompany = CompanyTestDataBuilder.aValidChildCompany()
                .withName("child-1")
                .withParentId(parentCompany.getId())
                .build();

        when(repository.getCompany(parentCompany.getId())).thenReturn(parentCompany);
        when(repository.saveAsChildCompany(any(), any())).thenReturn(expectedChildCompany);

        Company actualCompany = service.createCompany(parentCompany.getId(), "child-1");

        assertThat(actualCompany.getName()).isEqualTo(expectedChildCompany.getName());
        assertThat(actualCompany.getParentId()).isEqualTo(parentCompany.getId());
    }

    @Test
    public void shouldUpdateCompanyChangeTheName() throws NotFoundException {
        Company company = CompanyTestDataBuilder.aCompany()
                .withId(1)
                .withName("old-name")
                .build();

        when(repository.getCompany(company.getId())).thenReturn(company);

        service.updateCompany(company.getId(), "new-name");

        company.setName("new-name");

        verify(repository, times(1)).updateCompany(company);
    }

    @Test
    public void shouldUpdateCompanyThrowNotFoundExceptionWhenCompanyIdIsNotValid() throws NotFoundException {
        when(repository.getCompany(1)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> service.updateCompany(1, anyString()));
    }

    @Test
    public void shouldDeleteCompanyWhenIdIsValid() throws NotFoundException {
        Company company = CompanyTestDataBuilder.aCompany()
                .withId(1)
                .build();

        when(repository.getCompany(company.getId())).thenReturn(company);

        service.deleteCompany(company.getId());

        verify(repository, times(1)).deleteCompany(company);
    }

    @Test
    public void shouldDeleteCompanyThrowNotFoundExceptionWhenCompanyIdIsNotValid() throws NotFoundException {
        when(repository.getCompany(1)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> service.deleteCompany(1));
    }
}
