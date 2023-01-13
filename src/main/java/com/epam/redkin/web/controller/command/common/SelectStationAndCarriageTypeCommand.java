package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.service.CarriageService;
import com.epam.redkin.model.service.RouteMappingService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.epam.redkin.web.controller.Path.PAGE_SELECT_STATION_AND_CARRIAGE_TYPE;

public class SelectStationAndCarriageTypeCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectStationAndCarriageTypeCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        RouteMappingService routeMappingService = AppContext.getInstance().getRouteMappingService();
        CarriageService carriageService = AppContext.getInstance().getCarriageService();

        String departureStation = request.getParameter("departure_station");
        String arrivalStation = request.getParameter("arrival_station");
        String trainId = request.getParameter("train_id");
        String userId = request.getParameter("user_id");
        String routesId = request.getParameter("routes_id");
        String station1 = request.getParameter("station1");
        String station2 = request.getParameter("station2");
        String travelTime = request.getParameter("travel_time");
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.parse(request.getParameter("departure_date"));
        } catch (DateTimeParseException e) {
            LOGGER.error("Incorrect date entered");
            throw new IncorrectDataException("Incorrect date entered", e);
        }
        List<MappingInfoDTO> allRoutToStationMappingListById = routeMappingService
                .getAllRoutToStationMappingListById(Integer.parseInt(routesId));
        List<Carriage> allCarList = carriageService.getCarByTrainId(Integer.parseInt(trainId));
        Set<CarriageType> carSet = new HashSet<>();
        for (Carriage car : allCarList) {
            carSet.add(car.getType());
        }
        request.setAttribute("departure_station", departureStation);
        request.setAttribute("arrival_station", arrivalStation);
        request.setAttribute("departure_date", departureDate);
        request.setAttribute("user_id", userId);
        request.setAttribute("station_list", allRoutToStationMappingListById);
        request.setAttribute("train_id", trainId);
        request.setAttribute("station1",station1);
        request.setAttribute("station2",station2);
        request.setAttribute("travel_time",travelTime);
        request.setAttribute("carTypeList", carSet);
        request.setAttribute("routs_id", routesId);
        return PAGE_SELECT_STATION_AND_CARRIAGE_TYPE;
    }
}
