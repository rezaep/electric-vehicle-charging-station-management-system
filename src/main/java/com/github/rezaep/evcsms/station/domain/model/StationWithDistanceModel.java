package com.github.rezaep.evcsms.station.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.locationtech.jts.geom.Geometry;

@Data
@EqualsAndHashCode(callSuper = true)
public class StationWithDistanceModel extends StationModel {
    Double distance;

    public StationWithDistanceModel(long id, String name, Geometry geometry, long companyId, Double distance) {
        super(id, name, geometry.getCoordinate().y, geometry.getCoordinate().x, companyId);
        this.distance = distance;
    }
}
