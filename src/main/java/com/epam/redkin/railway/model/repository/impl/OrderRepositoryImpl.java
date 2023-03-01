package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.dto.BookingDTO;
import com.epam.redkin.railway.model.dto.ReservationDTO;
import com.epam.redkin.railway.model.entity.*;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.OrderRepository;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository, Constants {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRepositoryImpl.class);
    private final DataSource dataSource;

    public OrderRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(Order order) throws DataBaseException {
        LOGGER.info("Started the method create. Order: " + order);
        int key = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            LOGGER.info("Transaction started");
            statement = connection.prepareStatement(ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, order.getOrderDate());
            statement.setInt(2, order.getRouteId());
            statement.setString(3, order.getDispatchStation());
            statement.setObject(4, order.getDispatchDate());
            statement.setString(5, order.getArrivalStation());
            statement.setObject(6, order.getArrivalDate());
            statement.setString(7, order.getTravelTime());
            statement.setString(8, order.getTrainNumber());
            statement.setString(9, order.getCarriageNumber());
            statement.setString(10, order.getCarriageType().name());
            statement.setInt(11, order.getCountOfSeats());
            statement.setString(12, order.getSeatNumber());
            statement.setString(13, order.getSeatsId());
            statement.setInt(14, order.getUser().getUserId());
            statement.setDouble(15, order.getPrice());
            statement.setString(16, order.getOrderStatus().name());
            statement.executeUpdate();
            connection.commit();
            LOGGER.info("Transaction done");
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
            LOGGER.info("Generated id= " + key);
        } catch (SQLException | NullPointerException e) {
            try {
                assert connection != null;
                connection.rollback();
                LOGGER.info("Transaction rollback");
            } catch (SQLException | NullPointerException ex) {
                LOGGER.error("Connection rollback error: " + ex);
                throw new DataBaseException("Connection rollback error: ", ex);
            }
            LOGGER.error("Cannot create order: " + e);
            throw new DataBaseException("Cannot create order = " + order, e);
        } finally {
            try {
                assert connection != null;
                connection.setAutoCommit(true);
                DbUtils.close(resultSet);
                DbUtils.close(statement);
                DbUtils.close(connection);
                LOGGER.info("Connection closed");
            } catch (SQLException | NullPointerException e) {
                LOGGER.error("Connection closing error: " + e);
            }
        }
        return key;
    }


    @Override
    public Order getById(int id) throws DataBaseException {
        LOGGER.info("Started public Order getById(int id), id= " + id);
        Order order = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ORDER_BY_ORDER_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = extractOrder(resultSet);
            }
            LOGGER.info("Extracted Order= " + order);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot extract order: " + e);
            throw new DataBaseException("Cannot extract order, order_id = " + id, e);
        }
        return order;
    }

    private Order extractOrder(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .id(resultSet.getInt(ID))
                .trainNumber(resultSet.getString(TRAIN_NUMBER))
                .carriageType(CarriageType.valueOf(resultSet.getString(CARRIAGE_TYPE)))
                .price(resultSet.getDouble(PRICE))
                .arrivalDate(resultSet.getObject(ARRIVAL_DATE, LocalDateTime.class))
                .dispatchDate(resultSet.getObject(DISPATCH_DATE, LocalDateTime.class))
                .user(extractUser(resultSet))
                .orderDate(resultSet.getObject(BOOKING_DATE, LocalDateTime.class))
                .orderStatus(OrderStatus.valueOf(resultSet.getString(STATUS)))
                .countOfSeats(resultSet.getInt(SEATS_COUNT))
                .arrivalStation(resultSet.getString(ARRIVAL_STATION))
                .dispatchStation(resultSet.getString(DISPATCH_STATION))
                .travelTime(resultSet.getString(TRAVEL_TIME))
                .routeId(resultSet.getInt(ROUTE_ID))
                .carriageNumber(resultSet.getString(CARRIAGE_NUMBER))
                .seatNumber(resultSet.getString(SEAT_NUMBER))
                .seatsId(resultSet.getString(SEATS_ID))
                .build();
    }

    private User extractUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .userId(resultSet.getInt(AppContextConstant.ID))
                .email(resultSet.getString(AppContextConstant.EMAIL))
                .password(resultSet.getString(AppContextConstant.PASSWORD))
                .firstName(resultSet.getString(AppContextConstant.FIRST_NAME))
                .lastName(resultSet.getString(AppContextConstant.LAST_NAME))
                .phone(resultSet.getString(AppContextConstant.PHONE_NUMBER))
                .birthDate(resultSet.getObject(AppContextConstant.BIRTH_DATE, LocalDate.class))
                .role(Role.valueOf(resultSet.getString(AppContextConstant.ROLE).toUpperCase()))
                .blocked(resultSet.getBoolean(AppContextConstant.BLOCKED))
                .token(resultSet.getString(AppContextConstant.LOGIN_TOKEN))
                .build();
    }

    @Override
    public boolean update(Order entity) {
        LOGGER.error("Unsupported operation exception");
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) {
        LOGGER.error("Unsupported operation exception");
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getOrders() throws DataBaseException {
        LOGGER.info("Started public List<Order> getOrders()");
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ORDER)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractOrder(resultSet));
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot extract List<Order>: " + e);
            throw new DataBaseException("Cannot extract List<Order>", e);
        } finally {
            try {
                DbUtils.close(resultSet);
            } catch (SQLException e) {
                LOGGER.error("Cannot close ResultSet " + e);
            }
        }
        LOGGER.info("\nExtracted orders: " + orders);
        return orders;
    }

    @Override
    public boolean updateOrderStatus(int orderId, OrderStatus status) throws DataBaseException {
        LOGGER.info("Started  public boolean updateOrderStatus(int orderId, OrderStatus status) with orderId= " + orderId
                + " and OrderStatus= " + status.name());
        Connection connection = null;
        PreparedStatement statement = null;
        boolean statusUpdate;
        try {
            LOGGER.info("Transaction started");
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_ORDER_STATUS);
            statement.setString(1, status.name());
            statement.setInt(2, orderId);
            statusUpdate = statement.executeUpdate() > 0;
            connection.commit();
            LOGGER.info("Transaction done with status: " + statusUpdate);
        } catch (SQLException | NullPointerException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException | NullPointerException ex) {
                LOGGER.error("Connection rollback error:", e);
                throw new DataBaseException("Connection rollback error", ex);
            }
            LOGGER.error("Cannot update order status: " + e);
            throw new DataBaseException("Can`t update order status. Order id = " + orderId + " status: " + status.name(), e);
        } finally {
            try {
                assert connection != null;
                connection.setAutoCommit(true);
                DbUtils.close(statement);
                DbUtils.close(connection);
                LOGGER.info("Connection closed");
            } catch (SQLException | NullPointerException e) {
                LOGGER.error("Connection closing error: " + e);
            }
        }
        return statusUpdate;
    }

    @Override
    public List<Order> getOrderByUserId(int userId) throws DataBaseException {
        LOGGER.info("Started --> getOrderByUserId() --> userId= " + userId);
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ORDER_BY_USER_ID)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractOrder(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot get List<Order>: ", e);
            throw new DataBaseException("Can't get order list by user id | ID= " + userId, e);
        }
        LOGGER.info("\nThe method getOrderByUserId() done, orders: " + orders);
        return orders;
    }

    @Override
    public Double getPriceOfSuccessfulOrders(int userId) throws DataBaseException {
        LOGGER.info("Started --> getPriceOfSuccessfulOrders() --> userId= " + userId);
        double price = 0.0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_THE_PRICE_OF_SUCCESSFUL_ORDERS)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                price = rs.getDouble(Constants.TOTAL);
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot get price: " + e);
            throw new DataBaseException("Can`t get price. user id= " + userId, e);
        }
        LOGGER.info("Total order price: " + price);
        return price;
    }

    @Override
    public void addReservation(Connection connection, ReservationDTO reservationDTO) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_RESERVATION);
            statement.setString(1, reservationDTO.getStatus());
            statement.setInt(2, reservationDTO.getStationId());
            statement.setInt(3, reservationDTO.getSeatId());
            statement.setInt(4, reservationDTO.getTrainId());
            statement.setInt(5, reservationDTO.getRouteId());
            statement.setInt(6, reservationDTO.getSequenceNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to insert reservation: " + e.getMessage());
            throw new DataBaseException("Failed to insert reservation.", e);
        }
    }

    @Override
    public int saveBooking(BookingDTO bookingDTO) {
        LOGGER.info("Started saving booking");
        int key = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            LOGGER.info("Transaction started");
            statement = connection.prepareStatement(SAVE_BOOKING, Statement.RETURN_GENERATED_KEYS);
            statement.setTimestamp(1, Timestamp.valueOf(bookingDTO.getBookingDate()));
            statement.setTimestamp(2, Timestamp.valueOf(bookingDTO.getDispatchDate()));
            statement.setTimestamp(3, Timestamp.valueOf(bookingDTO.getArrivalDate()));
            statement.setString(4, bookingDTO.getTravelTime());
            statement.setDouble(5, bookingDTO.getPrice());
            statement.setString(6, bookingDTO.getBookingStatus().toString());
            statement.setInt(7, bookingDTO.getUserId());
            statement.setInt(8, bookingDTO.getRouteId());
            statement.setInt(9, bookingDTO.getTrainId());
            statement.setInt(10, bookingDTO.getDispatchStationId());
            statement.setInt(11, bookingDTO.getArrivalStationId());
            statement.setInt(12, bookingDTO.getCarriageId());
            statement.executeUpdate();
            connection.commit();
            LOGGER.info("Transaction done");
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
            LOGGER.info("Generated id= " + key);
        } catch (SQLException e) {
            try {
                assert connection != null;
                connection.rollback();
                LOGGER.info("Transaction rollback");
            } catch (SQLException | NullPointerException ex) {
                LOGGER.error("Connection rollback error: " + ex);
                throw new DataBaseException("Connection rollback error: ", ex);
            }
            LOGGER.error("Cannot save booking: " + e);
            throw new DataBaseException("Cannot save booking", e);
        } finally {
            try {
                assert connection != null;
                connection.setAutoCommit(true);
                DbUtils.close(resultSet);
                DbUtils.close(statement);
                DbUtils.close(connection);
                LOGGER.info("Connection closed");
            } catch (SQLException | NullPointerException e) {
                LOGGER.error("Connection closing error: " + e);
            }
        }
        return key;
    }

    @Override
    public void saveBookingSeat(int bookingId, String seatId) {
        LOGGER.info("Started saving booked seat bookingId={}, seatId={}", bookingId, seatId);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_BOOKED_SEAT)) {
            statement.setInt(1, bookingId);
            statement.setString(2, seatId);
            int affectedRow = statement.executeUpdate();
            LOGGER.info("Number of rows affected {}", affectedRow);
        } catch (SQLException e) {
            LOGGER.error("Saving booked seat error: " + e);
            throw new DataBaseException("Saving booked seat error", e);
        }
    }

}
