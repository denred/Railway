package com.epam.redkin.railway.web.tag;


import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimePeriodTag extends SimpleTagSupport {

    private String dateFrom;
    private String dateTo;
    private String locale;

    @Override
    public void doTag() throws IOException {
        Duration between;
        if (locale.equals("en")) {
            between = Duration.between(LocalDateTime.parse(dateFrom), LocalDateTime.parse(dateTo));
            getJspContext().getOut().print(String.format("%s h :%02d min", between.toHours() % 24, between.toMinutes() % 60));
        }
        if (locale.equals("ua")) {
            between = Duration.between(LocalDateTime.parse(dateFrom), LocalDateTime.parse(dateTo));
            getJspContext().getOut().print(String.format("%s год %02d хв", between.toHours() % 24, between.toMinutes() % 60));
        }
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
