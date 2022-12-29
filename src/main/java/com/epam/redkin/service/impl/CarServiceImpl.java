package com.epam.redkin.service.impl;


import com.epam.redkin.model.dto.CarriageDTO;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.entity.Seat;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.repository.CarriageRepository;
import com.epam.redkin.model.repository.SeatRepo;
import com.epam.redkin.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SuppressWarnings({"ALL", "FieldMayBeFinal"})
public class CarServiceImpl implements CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

    private CarriageRepository carRepository;
    private SeatRepo seatRepository;

    public CarServiceImpl(CarriageRepository carRepository, SeatRepo seatRepository) {
        this.carRepository = carRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void updateCar(CarriageDTO carriageDTO) {
        Carriage car = getCarFromCarDto(carriageDTO);
        carRepository.update(car);
        int countSeatBusy = seatRepository.getSeatsCarriageBusy(carriageDTO.getCarId());
        int countSeat = seatRepository.getSeatsCarriage(carriageDTO.getCarId());
        if (countSeatBusy == 0) {
            if (countSeat > carriageDTO.getSeats()) {
                seatRepository.deleteAllSeatsByCarId(carriageDTO.getCarId());
                for (int i = 1; i <= carriageDTO.getSeats(); i++) {
                    Seat seat = getSeatFromCarDto(carriageDTO, String.valueOf(i));
                    seatRepository.create(seat);
                }
            }
            if (countSeat < carriageDTO.getSeats()) {
                for (int i = countSeat + 1; i <= carriageDTO.getSeats(); i++) {
                    Seat seat = getSeatFromCarDto(carriageDTO, String.valueOf(i));
                    seatRepository.create(seat);
                }
            }
        } else {
            IncorrectDataException e = new IncorrectDataException("Can`t edit car because there are ordered seats");
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Carriage getCarById(int carId) {
        return carRepository.read(carId);
    }

    @Override
    public List<Carriage> getCarByTrainId(int trainId) {
        return carRepository.getCarriagesByTrainId(trainId);
    }

    @Override
    public void addCar(CarriageDTO carriageDTO) {
        Carriage car = getCarFromCarDto(carriageDTO);
        int carId = carRepository.create(car);
        carriageDTO.setCarId(carId);
        for (int i = 1; i <= carriageDTO.getSeats(); i++) {
            Seat seat = getSeatFromCarDto(carriageDTO, String.valueOf(i));
            seatRepository.create(seat);
        }
    }

    @Override
    public List<Carriage> getCarByTrainIdAndCarType(int trainId, String carType) {
        return carRepository.getCarriagesByTrainIdAndType(trainId, carType);
    }

    @Override
    public void removeCar(int carId) {
        seatRepository.deleteAllSeatsByCarId(carId);
        carRepository.delete(carId);
    }

    @Override
    public List<CarriageDTO> getAllCarList() {
        List<CarriageDTO> result = carRepository.getAllCarriageDTOList();
        for (CarriageDTO car : result) {
            int seat = seatRepository.getSeatsCarriage(car.getCarId());
            car.setSeats(seat);
            calculatePrice(car);
        }
        return result;
    }

    private Seat getSeatFromCarDto(CarriageDTO carriageDTO, String seatNumber) {
        Seat seat = new Seat();
        seat.setId(carriageDTO.getCarId());
        seat.setSeatNumber(seatNumber);
        seat.setBusy(false);
        return seat;
    }

    private Carriage getCarFromCarDto(CarriageDTO carriageDTO) {
        Carriage car = new Carriage();
        car.setCarriageId(carriageDTO.getCarId());
        car.setType(carriageDTO.getCarType());
        car.setNumber(carriageDTO.getCarNumber());
        car.setTrainId(carriageDTO.getTrainId());
        return car;
    }

    private void calculatePrice(CarriageDTO car) {
        car.setPrice(car.getCarType().getPrice());
    }

}
