package com.epam.redkin.web.controller;


import com.epam.redkin.model.entity.Order;
import com.epam.redkin.model.entity.OrderStatus;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.service.OrderService;
import com.epam.redkin.util.constants.AppContextConstant;
import com.epam.redkin.validator.OrderValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@WebServlet("/administrator_edit_info_order")
public class AdministratorEditInfoOrderController extends HttpServlet {
    //private static final Logger LOGGER = Logger.getLogger(AdministratorEditInfoOrderController.class);
    private OrderService orderService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OrderValidator orderValidator = new OrderValidator();
        Order order = new Order();
        String orderId = request.getParameter("order_id");
        OrderStatus status;
        try {
            status = OrderStatus.valueOf(request.getParameter("order_status"));
        } catch (IllegalArgumentException e) {
            //LOGGER.error("Incorrect data entered");
            throw new IncorrectDataException("Incorrect data entered", e);
        }
        orderValidator.isValidOrder(order);
        orderService.updateOrderStatus(Integer.parseInt(orderId), status);
        response.sendRedirect("administrator_account");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("order_id");
        Order order = orderService.getOrderById(Integer.parseInt(orderId));
        request.setAttribute("current_order", order);
        List<OrderStatus> orderStatusList = new ArrayList<>(EnumSet.allOf(OrderStatus.class));
        request.setAttribute("statusList", orderStatusList);

        request.getRequestDispatcher("WEB-INF/jsp/administratorEditInfoOrder.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) {
        orderService = (OrderService) config.getServletContext().getAttribute(AppContextConstant.ORDER_SERVICE);
        //LOGGER.trace("administrator_edit_info_order Servlet init");

    }
}
