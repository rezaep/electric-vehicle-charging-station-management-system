package com.github.rezaep.evcsms.station.service;

import com.github.rezaep.evcsms.company.domain.entity.Company;
import com.github.rezaep.evcsms.company.service.CompanyService;
import com.github.rezaep.evcsms.exception.NotFoundException;
import com.github.rezaep.evcsms.station.domain.entity.Station;
import com.github.rezaep.evcsms.station.domain.model.StationWithDistanceModel;
import com.github.rezaep.evcsms.station.repository.StationRepository;
import com.github.rezaep.evcsms.util.GeoUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository repository;
    private final CompanyService companyService;

    public Station getStation(long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public Station createStation(long companyId, String name, double latitude, double longitude) throws NotFoundException {
        Company company = companyService.getCompany(companyId);

        Point location = GeoUtils.createPoint(latitude, longitude);

        Station station = new Station(name, location, company);

        repository.save(station);

        return station;
    }

    public Station updateStation(long id, String name, double latitude, double longitude) throws NotFoundException {
        Station station = getStation(id);

        Point location = GeoUtils.createPoint(latitude, longitude);

        station.setName(name);
        station.setLocation(location);

        repository.save(station);

        return station;
    }

    public void deleteStation(long id) throws NotFoundException {
        Station station = getStation(id);

        repository.delete(station);
    }

    public List<StationWithDistanceModel> searchStations(Long companyId, double latitude, double longitude, double radius)
            throws NotFoundException {
        Set<Long> companyIds = null;

        if (companyId != null && companyId != 0) {
            Company company = companyService.getCompany(companyId);
            companyIds = companyService.getCompanyAndChildrenIds(company);
        }

        Point center = GeoUtils.createPoint(latitude, longitude);
        Geometry area = GeoUtils.createCircle(center, radius * 1000);

        return repository.findWithDistance(companyIds, area, center);
    }
}
