package com.github.rezaep.evcsms.station.controller.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateStationRequest {
    private long companyId;
    @NotNull
    private String name;
    private double lat;
    private double lng;
}
