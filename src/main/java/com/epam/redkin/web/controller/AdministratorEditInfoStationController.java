package com.epam.redkin.web.controller;


import com.epam.redkin.model.entity.Station;
import com.epam.redkin.service.StationService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.StationValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/administrator_edit_info_station")
public class AdministratorEditInfoStationController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorEditInfoStationController.class);
    private StationService stationService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StationValidator stationValidator = new StationValidator();
        Station station = new Station();
        station.setId(Integer.parseInt(request.getParameter("station_id")));
        station.setStation(request.getParameter("station"));
        stationValidator.isValidStation(station);
        stationService.updateStation(station);
        response.sendRedirect("administrator_account");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stationId = request.getParameter("station");
        Station station = stationService.getStationById(Integer.parseInt(stationId));
        request.setAttribute("current_station", station);
        request.getRequestDispatcher("WEB-INF/jsp/administratorEditInfoStation.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        stationService = (StationService) config.getServletContext().getAttribute(AppContextConstant.STATION_SERVICE);
        LOGGER.trace("administrator_edit_info_station Servlet init");

    }
}
