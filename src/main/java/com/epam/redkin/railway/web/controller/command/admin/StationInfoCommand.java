package com.epam.redkin.railway.web.controller.command.admin;

import com.epam.redkin.railway.model.entity.Station;
import com.epam.redkin.railway.model.service.StationService;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StationInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationInfoCommand.class);
    private static final int RECORDS_PER_PAGE = 5;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        StationService stationService = AppContext.getInstance().getStationService();
        int page = 1;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<Station> stationList = stationService.getStationListByCurrentPage(
                (page - 1) * RECORDS_PER_PAGE,
                RECORDS_PER_PAGE * page);
        int noOfRecords = stationService.getStationListSize();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        request.setAttribute("recordsPerPage", RECORDS_PER_PAGE);
        request.setAttribute("station_list", stationList);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        LOGGER.info("done");
        return Path.PAGE_ADMIN_INFO_STATION;
    }
}
