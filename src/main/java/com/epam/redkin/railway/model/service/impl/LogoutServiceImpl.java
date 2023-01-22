package com.epam.redkin.railway.model.service.impl;

import com.epam.redkin.railway.model.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;


public class LogoutServiceImpl implements LogoutService {

    public LogoutServiceImpl() {
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
        request.getSession(false);
    }
}
