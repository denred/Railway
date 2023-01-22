package com.epam.redkin.railway.model.service;



import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.dto.RoutsOrderDTO;
import com.epam.redkin.railway.model.entity.Route;

import java.time.LocalDateTime;
import java.util.List;

public interface RouteService {


    void addRout(Route route);

    void removeRout(int routsId);


    void updateRoute(Route route);

    List<RouteInfoDTO> getAllRouteList();
    List<RouteInfoDTO> getRouteListByCurrentRecordAndRecordsPerPage(int currentPage, int recordsPerPage, String filter, String filterValue);
    int getRouteListSize();


    RouteInfoDTO getRoutById(int routsId);

    List<RoutsOrderDTO> getRouteListWithParameters(String departureStation, String arrivalStation, LocalDateTime departureDate);

    void fillAvailableSeats(List<RoutsOrderDTO> routeOrderDTOList);
}
