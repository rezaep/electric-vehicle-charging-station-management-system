package com.github.rezaep.evcsms.station.model;

import com.github.rezaep.evcsms.company.domain.entity.Company;
import com.github.rezaep.evcsms.company.model.CompanyTestDataBuilder;
import com.github.rezaep.evcsms.station.domain.entity.Station;
import com.github.rezaep.evcsms.util.GeoUtils;
import org.locationtech.jts.geom.Point;

public class StationTestDataBuilder {
    private long id;
    private String name;
    private Point location;
    private Company company;

    private StationTestDataBuilder() {

    }

    public static StationTestDataBuilder aStation() {
        return new StationTestDataBuilder();
    }

    public static StationTestDataBuilder aValidStation() {
        Point location = GeoUtils.createPoint(35.696898, 51.401939);

        Company company = CompanyTestDataBuilder.aCompany()
                .build();

        return new StationTestDataBuilder()
                .withId(1)
                .withName("station-1")
                .withLocation(location)
                .withCompany(company);
    }

    public StationTestDataBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public StationTestDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public StationTestDataBuilder withLocation(Point location) {
        this.location = location;
        return this;
    }

    public StationTestDataBuilder withCompany(Company company) {
        this.company = company;
        return this;
    }

    public StationTestDataBuilder but() {
        return StationTestDataBuilder
                .aStation()
                .withId(id)
                .withName(name)
                .withLocation(location)
                .withCompany(company);
    }

    public Station build() {
        Station station = new Station();
        station.setId(id);
        station.setName(name);
        station.setLocation(location);
        station.setCompany(company);

        return station;
    }
}
