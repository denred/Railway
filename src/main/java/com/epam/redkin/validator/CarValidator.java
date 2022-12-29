package com.epam.redkin.validator;

import com.epam.redkin.model.dto.CarDto;
import com.epam.redkin.model.exception.IncorrectDataException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class CarValidator {
   // private static final Logger LOGGER = Logger.getLogger(CarValidator.class);
    private static final String CAR_NUMBER = "^(?<![-\\d])(?<!\\d[.,])\\d*[1-9](?![.,]?\\d){1,2}$";
    private static final String COUNT_OF_SEATS = "^(?<![-\\d])(?<!\\d[.,])\\d*[1-9](?![.,]?\\d){1,2}$";

    public void isValidCar(CarDto carDto) {
        Map<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(carDto.getCarNumber()) || !ValidatorUtils.isMatch(CAR_NUMBER, carDto.getCarNumber())) {
            errors.put("Incorrect format, type something like \"123\"", carDto.getCarNumber());
        }

        if (StringUtils.isBlank(String.valueOf(carDto.getSeats())) || !ValidatorUtils.isMatch(COUNT_OF_SEATS, String.valueOf(carDto.getSeats()))) {
            errors.put("Incorrect format, type something like \"123\"", String.valueOf(carDto.getSeats()));
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
           // LOGGER.error(e);
            throw e;
        }
    }
}
