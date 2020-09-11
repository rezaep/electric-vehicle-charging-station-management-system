package com.github.rezaep.evcsms.company.controller;

import com.github.rezaep.evcsms.company.controller.model.CreateCompanyRequest;
import com.github.rezaep.evcsms.company.controller.model.UpdateCompanyRequest;
import com.github.rezaep.evcsms.company.domain.entity.Company;
import com.github.rezaep.evcsms.company.domain.model.CompanyModel;
import com.github.rezaep.evcsms.company.domain.model.DetailedCompanyModel;
import com.github.rezaep.evcsms.company.service.CompanyService;
import com.github.rezaep.evcsms.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService service;

    @GetMapping
    public Page<CompanyModel> getCompanies(Pageable pageable) {
        return service.getCompanies(pageable)
                .map(this::convertToModel);
    }

    @GetMapping("{id}")
    public DetailedCompanyModel getCompany(@PathVariable long id) throws NotFoundException {
        Company company = service.getCompany(id);
        List<Company> children = service.getChildren(company);

        return convertToDetailedModel(company, children);
    }

    @PostMapping
    public CompanyModel createCompany(@Valid @RequestBody CreateCompanyRequest request) throws NotFoundException {
        Company company = service.createCompany(request.getParentId(), request.getName());

        return convertToModel(company);
    }

    @PutMapping("{id}")
    public CompanyModel updateCompany(@PathVariable long id, @Valid @RequestBody UpdateCompanyRequest request) throws NotFoundException {
        Company company = service.updateCompany(id, request.getName());

        return convertToModel(company);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable long id) throws NotFoundException {
        service.deleteCompany(id);

        return ResponseEntity.ok().build();
    }

    private DetailedCompanyModel convertToDetailedModel(Company company, List<Company> children) {
        DetailedCompanyModel model = new DetailedCompanyModel();

        model.setId(company.getId())
                .setName(company.getName());

        List<CompanyModel> childrenModels = children.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());

        model.setChildren(childrenModels);

        if (CollectionUtils.isEmpty(model.getStations())) {
            model.setStations(new ArrayList<>());
        }

        return model;
    }

    private CompanyModel convertToModel(Company company) {
        return new CompanyModel()
                .setId(company.getId())
                .setName(company.getName());
    }
}
