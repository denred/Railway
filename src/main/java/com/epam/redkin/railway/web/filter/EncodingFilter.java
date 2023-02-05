package com.epam.redkin.railway.web.filter;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EncodingFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncodingFilter.class);
    private String encoding;

    public void init(FilterConfig config) {
        LOGGER.info("Filter initialization starts");
        encoding = config.getInitParameter("encoding");
        LOGGER.trace("Encoding from web.xml --> " + encoding);
        LOGGER.info("Filter initialization finished");
    }

    public void destroy() {
        LOGGER.debug("Filter destruction starts");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestEncoding = request.getCharacterEncoding();

        if (requestEncoding == null) {
            LOGGER.trace("Request encoding = null, set encoding --> " + encoding);
            request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }
}
