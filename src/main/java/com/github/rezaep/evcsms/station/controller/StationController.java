package com.github.rezaep.evcsms.station.controller;

import com.github.rezaep.evcsms.exception.NotFoundException;
import com.github.rezaep.evcsms.station.controller.model.CreateStationRequest;
import com.github.rezaep.evcsms.station.controller.model.UpdateStationRequest;
import com.github.rezaep.evcsms.station.domain.entity.Station;
import com.github.rezaep.evcsms.station.domain.model.StationModel;
import com.github.rezaep.evcsms.station.domain.model.StationWithDistanceModel;
import com.github.rezaep.evcsms.station.service.StationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("station")
public class StationController {
    private final StationService service;
    private final ModelMapper modelMapper;

    public StationController(StationService service) {
        this.service = service;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("{id}")
    public StationModel getStation(@PathVariable long id) throws NotFoundException {
        Station station = service.getStation(id);
        return convertToModel(station);
    }

    @GetMapping("search")
    public List<StationWithDistanceModel> searchStations(@RequestParam(required = false) Long companyId,
                                                         @RequestParam double latitude,
                                                         @RequestParam double longitude,
                                                         @RequestParam double radius) throws NotFoundException {
        return service.searchStations(companyId, latitude, longitude, radius);
    }

    @PostMapping
    public StationModel createStation(@Valid @RequestBody CreateStationRequest request) throws NotFoundException {
        Station station = service.createStation(request.getCompanyId(), request.getName(), request.getLat(), request.getLng());
        return convertToModel(station);
    }

    @PutMapping("{id}")
    public StationModel updateStation(@PathVariable long id, @Valid @RequestBody UpdateStationRequest request) throws NotFoundException {
        Station station = service.updateStation(id, request.getName(), request.getLat(), request.getLng());
        return convertToModel(station);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStation(@PathVariable long id) throws NotFoundException {
        service.deleteStation(id);

        return ResponseEntity.ok().build();
    }

    private StationModel convertToModel(Station station) {
        return modelMapper.map(station, StationModel.class);
    }
}
