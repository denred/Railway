package com.epam.redkin.railway.web.controller.command.common;

import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.model.validator.RegistrationValidator;
import com.epam.redkin.railway.web.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;
import static com.epam.redkin.railway.web.controller.Path.PAGE_PROFILE;

public class ProfileCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Started");
        UserService userService = AppContext.getInstance().getUserService();
        RegistrationValidator registrationValidator = new RegistrationValidator();
        HttpSession session = request.getSession();
        User user = userService.read(((User) session.getAttribute(SESSION_USER)).getUserId());

        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String password = request.getParameter(PASSWORD);
        String passwordConfirmation = request.getParameter(PASSWORD_CONFIRMATION);

        if (!StringUtils.isAllBlank(firstName, lastName, phoneNumber, password, passwordConfirmation)) {
            User newUser = User.builder()
                    .userId(user.getUserId())
                    .firstName(firstName)
                    .lastName(lastName)
                    .birthDate(user.getBirthDate())
                    .phone(phoneNumber)
                    .role(user.getRole())
                    .email(user.getEmail())
                    .blocked(user.isBlocked())
                    .registrationDate(user.getRegistrationDate())
                    .password(StringUtils.isNoneBlank(password) &&
                            password.equals(passwordConfirmation) ? BCrypt.hashpw(password, BCrypt.gensalt()) : user.getPassword())
                    .build();

            registrationValidator.isValidClientRegister(newUser);

            boolean userUpdated = userService.updateUser(newUser);
            if (userUpdated) {
                User updatedUser = userService.read(newUser.getUserId());
                session.removeAttribute(SESSION_USER);
                session.setAttribute(SESSION_USER, updatedUser);
            }
        }
        user = userService.read(user.getUserId());

        request.setAttribute(FIRST_NAME, user.getFirstName());
        request.setAttribute(LAST_NAME, user.getLastName());
        request.setAttribute(PHONE_NUMBER, user.getPhone());
        request.setAttribute(EMAIL, user.getEmail());

        return PAGE_PROFILE;
    }
}
