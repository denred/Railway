package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.web.controller.command.Command;
import com.epam.redkin.railway.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HomeCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Loaded page: {}", Path.PAGE_HOME);
        return Router.forward(Path.PAGE_HOME);
    }
}