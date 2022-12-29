package com.epam.redkin.validator;


import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.IncorrectDataException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

public class RegistrationValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationValidator.class);
    private static final String EMAIL = "[a-zA-Z0-9._-][a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
    private static final String PASSWORD = "[\\S+]{8,32}";
    private static final String USER_NAME = "[a-zA-Zа-яА-яёЁ]{2,15}";
    private static final String USER_SURNAME = "[a-zA-Zа-яА-яёЁ]{2,15}";
    private static final String USER_DATE_OF_BIRTH = "[0-9]{4}-[0-9]{2}\\-[0-9]{2}";
    private static final String USER_PHONE_NUMBER = "\\+[3]{1}[8]{1}[0]{1}[0-9]{9}";


    public void isValidClientRegister(User user) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(user.getEmail()) || !ValidatorUtils.isMatch(EMAIL, user.getEmail())) {
            errors.put("Incorrect format, type something like \"user@gmail.com\"", user.getEmail());
        }
        if (StringUtils.isBlank(user.getPassword()) || !ValidatorUtils.isMatch(PASSWORD, user.getPassword())) {
            errors.put("Incorrect format, type something like \"passw0r$4\"", user.getPassword());
        }
        if (StringUtils.isBlank(user.getFirstName()) || !ValidatorUtils.isMatch(USER_NAME, user.getFirstName())) {
            errors.put("Incorrect format, type something like \"Alexandr or Александр\"", user.getFirstName());
        }
        if (StringUtils.isBlank(user.getLastName()) || !ValidatorUtils.isMatch(USER_SURNAME, user.getLastName())) {
            errors.put("Incorrect format, type something like \"Petrov or Петров\"", user.getLastName());
        }
        if (Objects.isNull(user.getBirthDate()) || !ValidatorUtils.isMatch(USER_DATE_OF_BIRTH, String.valueOf(user.getBirthDate()))) {
            errors.put("Incorrect format, type something like \"12-01-1993\"", String.valueOf(user.getBirthDate()));
        }

        if (StringUtils.isBlank(user.getPhone()) || !ValidatorUtils.isMatch(USER_PHONE_NUMBER, user.getPhone())) {
            errors.put("Incorrect format, type something like \"+380965467832\"", user.getPhone());
        }

        if (!errors.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> entry : errors.entrySet()) {
                builder.append(entry.getKey())
                        .append("Entered data:&nbsp;")
                        .append(entry.getValue())
                        .append(";<br/>\n");
            }
            IncorrectDataException e = new IncorrectDataException(builder.toString());
            LOGGER.error(e.getMessage());
            throw e;
        }
    }
}