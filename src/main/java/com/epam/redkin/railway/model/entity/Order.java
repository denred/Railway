package com.epam.redkin.railway.model.entity;

import com.epam.redkin.railway.model.builder.OrderBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private int id;
    private LocalDateTime orderDate;
    private int routeId;
    private String routeName;
    private String dispatchStation;
    private LocalDateTime dispatchDate;
    private String arrivalStation;
    private LocalDateTime arrivalDate;
    private String travelTime;
    private String trainNumber;
    private String carriageNumber;
    private CarriageType carriageType;
    private int countOfSeats;
    private String seatNumber;
    private String seatsId;
    private User user;
    private double price;
    private OrderStatus orderStatus;

    public Order() {

    }

    public Order(int id, String trainNumber, CarriageType carrType, double price, LocalDateTime arrivalDate, LocalDateTime dispatchDate, User user, LocalDateTime orderDate, OrderStatus orderStatus, int countOfSeats, String arrivalStation, String dispatchStation, String travelTime, int routeId, String carriageNumber, String seatNumber, String seatsId) {
        this.id = id;
        this.trainNumber = trainNumber;
        this.carriageType = carrType;
        this.price = price;
        this.arrivalDate = arrivalDate;
        this.dispatchDate = dispatchDate;
        this.user = user;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.countOfSeats = countOfSeats;
        this.arrivalStation = arrivalStation;
        this.dispatchStation = dispatchStation;
        this.travelTime = travelTime;
        this.routeId = routeId;
        this.carriageNumber = carriageNumber;
        this.seatNumber = seatNumber;
        this.seatsId = seatsId;
    }

    public Order(OrderBuilder builder) {
        this.id = builder.getId();
        this.trainNumber = builder.getTrainNumber();
        this.carriageType = builder.getCarriageType();
        this.price = builder.getPrice();
        this.arrivalDate = builder.getArrivalDate();
        this.dispatchDate = builder.getDispatchDate();
        this.user = builder.getUser();
        this.orderDate = builder.getOrderDate();
        this.orderStatus = builder.getOrderStatus();
        this.countOfSeats = builder.getCountOfSeats();
        this.arrivalStation = builder.getArrivalStation();
        this.dispatchStation = builder.getDispatchStation();
        this.travelTime = builder.getTravelTime();
        this.routeId = builder.getRouteId();
        this.carriageNumber = builder.getCarriageNumber();
        this.seatNumber = builder.getSeatNumber();
        this.seatsId = builder.getSeatsId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public CarriageType getCarriageType() {
        return carriageType;
    }

    public void setCarriageType(CarriageType carrType) {
        this.carriageType = carrType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDateTime getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(LocalDateTime dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getCountOfSeats() {
        return countOfSeats;
    }

    public void setCountOfSeats(int countOfSeats) {
        this.countOfSeats = countOfSeats;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public String getDispatchStation() {
        return dispatchStation;
    }

    public void setDispatchStation(String dispatchStation) {
        this.dispatchStation = dispatchStation;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getCarriageNumber() {
        return carriageNumber;
    }

    public void setCarriageNumber(String carriageNumber) {
        this.carriageNumber = carriageNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatsId() {
        return seatsId;
    }

    public void setSeatsId(String seatsId) {
        this.seatsId = seatsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Double.compare(order.getPrice(), getPrice()) == 0 && getCountOfSeats() == order.getCountOfSeats() && getRouteId() == order.getRouteId() && getTrainNumber().equals(order.getTrainNumber()) && getCarriageType() == order.getCarriageType() && getArrivalDate().equals(order.getArrivalDate()) && getDispatchDate().equals(order.getDispatchDate()) && getUser().equals(order.getUser()) && getOrderDate().equals(order.getOrderDate()) && getOrderStatus() == order.getOrderStatus() && getArrivalStation().equals(order.getArrivalStation()) && getDispatchStation().equals(order.getDispatchStation()) && getTravelTime().equals(order.getTravelTime()) && getCarriageNumber().equals(order.getCarriageNumber()) && getSeatNumber().equals(order.getSeatNumber()) && getSeatsId().equals(order.getSeatsId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainNumber(), getCarriageType(), getPrice(), getArrivalDate(), getDispatchDate(), getUser(), getOrderDate(), getOrderStatus(), getCountOfSeats(), getArrivalStation(), getDispatchStation(), getTravelTime(), getRouteId(), getCarriageNumber(), getSeatNumber(), getSeatsId());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", trainNumber='" + trainNumber + '\'' +
                ", carrType=" + carriageType +
                ", price=" + price +
                ", arrivalDate=" + arrivalDate +
                ", dispatchDate=" + dispatchDate +
                ", user=" + user +
                ", orderDate=" + orderDate +
                ", orderStatus=" + orderStatus +
                ", countOfSeats=" + countOfSeats +
                ", arrivalStation='" + arrivalStation + '\'' +
                ", dispatchStation='" + dispatchStation + '\'' +
                ", travelTime='" + travelTime + '\'' +
                ", routeId=" + routeId +
                ", carriageNumber='" + carriageNumber + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", seatsId='" + seatsId + '\'' +
                '}';
    }
}
