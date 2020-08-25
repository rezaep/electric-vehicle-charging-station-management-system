package com.github.rezaep.evcsms.company.controller.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateCompanyRequest {
    private Long parentId;
    @NotEmpty
    private String name;
}
