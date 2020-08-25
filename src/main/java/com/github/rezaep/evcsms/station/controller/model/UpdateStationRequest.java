package com.github.rezaep.evcsms.station.controller.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateStationRequest {
    @NotNull
    private String name;
    private double lat;
    private double lng;
}
