package com.github.rezaep.evcsms.station.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationModel {
    private long id;
    private String name;
    private double lat;
    private double lng;
    private long companyId;
}
