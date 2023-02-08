package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.model.dto.RoutsOrderDTO;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.model.validator.SearchValidator;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

public class SearchRoutesCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRoutesCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        Router router = new Router();
        router.setRouteType(Router.RouteType.FORWARD);
        router.setPagePath(Path.PAGE_SEARCH_ROUTES);

        RouteService routeService = AppContext.getInstance().getRouteService();
        SearchValidator searchValidator = new SearchValidator();

        String departureStation = request.getParameter(DEPARTURE_STATION);
        String arrivalStation = request.getParameter(ARRIVAL_STATION);
        String startDate = request.getParameter(DEPARTURE_DATE);
        String startTime = request.getParameter(DEPARTURE_TIME);

        if (StringUtils.isNoneBlank(departureStation, arrivalStation, startDate, startTime)) {
            LocalDateTime departureDate = routeService.getDepartureDate(startDate, startTime);
            String errorMessage = searchValidator.isValidSearch(departureStation, arrivalStation);
            HttpSession session = request.getSession();
            if (StringUtils.isNoneBlank(errorMessage)) {
                router.setRouteType(Router.RouteType.REDIRECT);
                router.setPagePath(Path.COMMAND_HOME);
                session.setAttribute(ERROR_MESSAGE, errorMessage);
                return router;
            }
            List<RoutsOrderDTO> routeOrderDTOList = routeService.getRouteOrderDtoList(departureStation, arrivalStation, departureDate);
            routeService.fillAvailableSeats(routeOrderDTOList);
            session.removeAttribute(ERROR_MESSAGE);
            session.setAttribute(ROUTE_ORDER_DTO_LIST, routeOrderDTOList);
            session.setAttribute(DEPARTURE_STATION, departureStation);
            session.setAttribute(ARRIVAL_STATION, arrivalStation);
            session.setAttribute(DEPARTURE_DATE, departureDate);
            router.setRouteType(Router.RouteType.REDIRECT);
            router.setPagePath(Path.COMMAND_SEARCH_ROUTES);
        }

        LOGGER.info("done");
        return router;
    }
}
