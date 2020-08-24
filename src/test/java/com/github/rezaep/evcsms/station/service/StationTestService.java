package com.github.rezaep.evcsms.station.service;

import com.github.rezaep.evcsms.company.domain.entity.Company;
import com.github.rezaep.evcsms.company.model.CompanyTestDataBuilder;
import com.github.rezaep.evcsms.company.service.CompanyService;
import com.github.rezaep.evcsms.exception.NotFoundException;
import com.github.rezaep.evcsms.station.domain.entity.Station;
import com.github.rezaep.evcsms.station.model.StationTestDataBuilder;
import com.github.rezaep.evcsms.station.repository.StationRepository;
import com.github.rezaep.evcsms.util.GeoUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StationTestService {
    @Mock
    private StationRepository repository;
    @Mock
    private CompanyService companyService;
    @InjectMocks
    private StationService service;

    @Test
    public void shouldGetStationReturnStationWhenIdIsValid() throws NotFoundException {
        Station expectedStation = StationTestDataBuilder.aValidStation()
                .withId(1)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(expectedStation));

        Station actualStation = service.getStation(1);

        assertThat(actualStation.getId()).isEqualTo(expectedStation.getId());
        assertThat(actualStation.getName()).isEqualTo(expectedStation.getName());
        assertThat(actualStation.getLocation()).isEqualTo(expectedStation.getLocation());
        assertThat(actualStation.getCompany()).isEqualTo(expectedStation.getCompany());
    }

    @Test
    public void shouldGetStationThrowNotFoundExceptionWhenStationIdIsNotValid() throws NotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getStation(1));
    }

    @Test
    public void shouldCreateStationWhenCompanyIdAndInputsAreValid() throws NotFoundException {
        Company company = CompanyTestDataBuilder.aValidCompany()
                .build();

        when(companyService.getCompany(company.getId())).thenReturn(company);

        Station station = StationTestDataBuilder.aValidStation()
                .withId(0)
                .withCompany(company)
                .build();

        service.createStation(company.getId(), station.getName(), station.getLocation().getY()
                , station.getLocation().getX());

        verify(repository, times(1)).save(station);
    }

    @Test
    public void shouldCreateStationThrowNotFoundExceptionWhenCompanyIdIsNotValid() throws NotFoundException {
        when(companyService.getCompany(1)).thenThrow(NotFoundException.class);

        Station station = StationTestDataBuilder.aValidStation()
                .build();

        assertThrows(NotFoundException.class, () -> service.createStation(1, station.getName()
                , station.getLocation().getX(), station.getLocation().getY()));
    }

    @Test
    public void shouldUpdateStationSave() throws NotFoundException {
        Station station = StationTestDataBuilder.aValidStation()
                .build();

        when(repository.findById(station.getId())).thenReturn(Optional.of(station));

        double latitude = 35.721988;
        double longitude = 51.35785;

        service.updateStation(station.getId(), "new-name", latitude, longitude);

        station.setName("new-name");
        station.setLocation(GeoUtils.createPoint(latitude, longitude));
        station.setName("new-name");

        verify(repository, times(1)).save(station);
    }

    @Test
    public void shouldUpdateStationThrowNotFoundExceptionWhenCompanyIdIsNotValid() throws NotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Station station = StationTestDataBuilder.aValidStation()
                .build();

        assertThrows(NotFoundException.class, () -> service.updateStation(1, station.getName()
                , station.getLocation().getX(), station.getLocation().getY()));
    }

    @Test
    public void shouldDeleteStationWhenIdIsValid() throws NotFoundException {
        Station station = StationTestDataBuilder.aValidStation()
                .withId(1)
                .build();

        when(repository.findById(station.getId())).thenReturn(Optional.of(station));

        service.deleteStation(station.getId());

        verify(repository, times(1)).delete(station);
    }

    @Test
    public void shouldDeleteStationThrowNotFoundExceptionWhenCompanyIdIsNotValid() throws NotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.deleteStation(1));
    }
}
