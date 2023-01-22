package com.epam.redkin.railway.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Train {
    private int id;
    private String number;
}
