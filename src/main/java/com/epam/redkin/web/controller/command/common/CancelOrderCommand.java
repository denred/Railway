package com.epam.redkin.web.controller.command.common;

import com.epam.redkin.service.OrderService;
import com.epam.redkin.web.controller.command.Command;
import com.epam.redkin.web.listener.AppContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.redkin.web.controller.Path.PAGE_HOME;

public class CancelOrderCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("started");
        OrderService orderService = AppContext.getInstance().getOrderService();
        String orderId = request.getParameter("order_id");
        orderService.cancelOrder(Integer.parseInt(orderId));
        LOGGER.info("done");
        return PAGE_HOME;
    }
}
