package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.service.UserService;
import com.epam.redkin.web.controller.Path;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.epam.redkin.web.controller.Path.*;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("LoginCommand started");
        UserService userService = AppContext.getInstance().getUserService();
        HttpSession session = request.getSession();

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String errorMessage;
        String forward = PAGE_LOGIN;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login or password can't be empty";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.debug("Login or password is empty");
            return forward;
        }
        User user = userService.isValidUser(login, password);
        LOGGER.debug("User successfully extracted");
        forward = COMMAND_HOME;
        session.setAttribute("user", user);
        session.setAttribute("dateTime", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        session.setAttribute(LOCALE, LOCALE_UA);
        LOGGER.debug("LoginCommand done");
        return forward;
    }
}