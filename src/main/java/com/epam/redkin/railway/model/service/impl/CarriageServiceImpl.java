package com.epam.redkin.railway.model.service.impl;


import com.epam.redkin.railway.model.dto.CarriageDTO;
import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.service.CarriageService;
import com.epam.redkin.railway.model.repository.CarriageRepository;
import com.epam.redkin.railway.model.repository.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@SuppressWarnings({"ALL", "FieldMayBeFinal"})
public class CarriageServiceImpl implements CarriageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageServiceImpl.class);
    private CarriageRepository carriageRepository;
    private SeatRepository seatRepository;

    public CarriageServiceImpl(CarriageRepository carriageRepository, SeatRepository seatRepository) {
        this.carriageRepository = carriageRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void updateCarriage(CarriageDTO carriageDTO) {
        Carriage car = getCarFromCarDto(carriageDTO);

            carriageRepository.update(car);

        int countSeatBusy = seatRepository.getBusySeatsCountByCarriageId(carriageDTO.getCarId());
        int countSeat = seatRepository.getSeatsCountByCarriageId(carriageDTO.getCarId());
        if (countSeatBusy == 0) {
            if (countSeat > carriageDTO.getSeats()) {
                seatRepository.deleteAllSeatsByCarriageId(carriageDTO.getCarId());
                for (int i = 1; i <= carriageDTO.getSeats(); i++) {
                    Seat seat = getSeatFromCarDto(carriageDTO, String.valueOf(i));
                    try {
                        seatRepository.create(seat);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (countSeat < carriageDTO.getSeats()) {
                for (int i = countSeat + 1; i <= carriageDTO.getSeats(); i++) {
                    Seat seat = getSeatFromCarDto(carriageDTO, String.valueOf(i));
                    LOGGER.debug("1" + seat.getSeatNumber() + " - " + seat.getCarriageId());
                    try {
                        seatRepository.create(seat);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            IncorrectDataException e = new IncorrectDataException("Can`t edit car because there are ordered seats");
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Carriage
    getCarriageById(int carriageId) {
        return carriageRepository.getById(carriageId);
    }

    @Override
    public List<Carriage> getCarriageByTrainId(int trainId) {
        return carriageRepository.getCarriagesByTrainId(trainId);
    }

    @Override
    public void addCarriage(CarriageDTO carriageDTO) {
        Carriage carriage = getCarFromCarDto(carriageDTO);
        int carriageId = carriage.getCarriageId();
        if (carriageRepository.getById(carriageId) == null) {
            try {
                carriageId = carriageRepository.create(carriage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        carriageDTO.setCarId(carriageId);
        for (int i = 1; i <= carriageDTO.getSeats(); i++) {
            Seat seat = getSeatFromCarDto(carriageDTO, String.valueOf(i));
            try {
                seatRepository.create(seat);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Carriage> getCarriageByTrainIdAndCarriageType(int trainId, String carType) {
        return carriageRepository.getCarriagesByTrainIdAndType(trainId, carType);
    }

    @Override
    public List<CarriageDTO> getCarriageDtoListPagination(int offset, int limit, Map<String, String> search) {
        List<CarriageDTO> result = carriageRepository.getCarriageDTOListPagination(offset, limit, search);
        for (CarriageDTO car : result) {
            int seat = seatRepository.getSeatsCountByCarriageId(car.getCarId());
            car.setSeats(seat);
            calculatePrice(car);
        }
        return result;
    }

    @Override
    public int getRouteListSize(Map<String, String> search) {
        return carriageRepository.getCountCarriagesByFilter(search);
    }

    @Override
    public Carriage getCarriageByNumber(String carriageNumber) {
        return carriageRepository.getCarriageByNumber(carriageNumber);
    }

    @Override
    public void removeCarriage(int carId) {
        seatRepository.deleteAllSeatsByCarriageId(carId);
        carriageRepository.delete(carId);
    }

    @Override
    public List<CarriageDTO> getCarriageDTOList() {
        List<CarriageDTO> result = carriageRepository.getCarriageDTOList();
        for (CarriageDTO car : result) {
            int seat = seatRepository.getSeatsCountByCarriageId(car.getCarId());
            car.setSeats(seat);
            calculatePrice(car);
        }
        return result;
    }

    private Seat getSeatFromCarDto(CarriageDTO carriageDTO, String seatNumber) {
        Seat seat = Seat.builder().build();
        seat.setCarriageId(carriageDTO.getCarId());
        seat.setSeatNumber(seatNumber);
        seat.setBusy(false);
        return seat;
    }

    private Carriage getCarFromCarDto(CarriageDTO carriageDTO) {
        return Carriage.builder()
                .carriageId(carriageDTO.getCarId())
                .type(carriageDTO.getCarriageType())
                .number(carriageDTO.getCarNumber())
                .trainId(carriageDTO.getTrainId())
                .build();
    }

    private void calculatePrice(CarriageDTO car) {
        car.setPrice(car.getCarriageType().getPrice());
    }

}
