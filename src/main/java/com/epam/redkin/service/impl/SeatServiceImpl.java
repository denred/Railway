package com.epam.redkin.service.impl;


import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Seat;
import com.epam.redkin.model.repository.SeatRepository;
import com.epam.redkin.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SeatServiceImpl implements SeatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatServiceImpl.class);
    private SeatRepository seatRepository;

    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public int getCountSeat(int carId) {
        return seatRepository.getSeatsCountByCarriageId(carId);
    }

    @Override
    public int getCountSeatByCarType(int trainId, CarriageType type) {
        return seatRepository.getSeatsCountByTrainIdAndByTypes(trainId, type);
    }

    @Override
    public List<Seat> getSeatByCarId(int carId) {
        return seatRepository.getListSeatsByCarriageId(carId);
    }

    @Override
    public List<Seat> getSeatsByIdBatch(List<String> seatsNumber) {
        return seatRepository.getListSeatsByIdBatch(seatsNumber);
    }

    @Override
    public List<String> getSeatsId(String seatId) {
        List<String> seatIdList = new ArrayList<>();
        seatIdList = Arrays.stream(seatId.replaceAll("\\[\\[", "")
                        .replaceAll("\\]\\]", "")
                        .split(","))
                .collect(Collectors.toList());

        return seatIdList;
    }
}
