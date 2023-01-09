package com.epam.redkin.service.impl;


import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.repository.StationRepository;
import com.epam.redkin.service.StationService;

import java.util.List;

public class StationServiceImpl implements StationService {

    private StationRepository stationRepository;


    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }


    @Override
    public List<Station> getAllStationList() {
        return stationRepository.getAllStations();
    }

    @Override
    public List<Station> getStationListByCurrentPage(int currentPage, int recordsPerPage) {
        List<Station> allRecords = stationRepository.getAllStations();
        return allRecords.subList(currentPage, Math.min(recordsPerPage, allRecords.size()));
    }

    @Override
    public int getStationListSize() {
        return stationRepository.getAllStations().size();
    }

    @Override
    public void updateStation(Station station) {
        stationRepository.update(station);
    }

    @Override
    public Station getStationById(int stationId) {
        return stationRepository.read(stationId);
    }

    @Override
    public void removeStation(int stationId) {
        stationRepository.delete(stationId);
    }

    @Override
    public void addStation(Station station) {
        stationRepository.create(station);
    }
}
