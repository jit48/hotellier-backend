package com.hotelier.dto;

import lombok.Data;

import java.util.List;

@Data
public class FoodOrderRequest {
    private String roomNumber;
    private List<FoodOrderDetailsDTO> foodOrderDetailsDTOList;
}

