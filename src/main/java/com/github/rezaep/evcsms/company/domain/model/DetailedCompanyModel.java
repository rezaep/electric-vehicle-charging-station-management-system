package com.github.rezaep.evcsms.company.domain.model;

import com.github.rezaep.evcsms.station.domain.model.StationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DetailedCompanyModel extends CompanyModel {
    private List<CompanyModel> children;
    private List<StationModel> stations;
}
