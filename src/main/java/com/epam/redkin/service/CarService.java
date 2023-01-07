package com.epam.redkin.service;


import com.epam.redkin.model.dto.CarriageDTO;
import com.epam.redkin.model.entity.Carriage;

import java.util.List;

public interface CarService {

    List<CarriageDTO> getAllCarList();


    void removeCar(int carId);


    List<Carriage> getCarByTrainId(int trainId);


    void addCarriage(CarriageDTO carriageDTO);


    Carriage getCarById(int carId);


    void updateCar(CarriageDTO carriageDTO);


    List<Carriage> getCarByTrainIdAndCarType(int trainId, String carType);

}
