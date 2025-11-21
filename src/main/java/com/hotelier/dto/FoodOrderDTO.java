package com.hotelier.dto;

import com.hotelier.model.FoodOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrderDTO {
    private Long id;
    private Long userId;
    private String userEmail;
    private String userName;
    private String item;
    private String description;
    private Integer quantity;
    private String specialInstructions;
    private FoodOrder.OrderStatus status;
    private LocalDateTime orderTime;
    private LocalDateTime deliveryTime;
    private String roomNumber;
    private LocalDateTime createdAt;
}

