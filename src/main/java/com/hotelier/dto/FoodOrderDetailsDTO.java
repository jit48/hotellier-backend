package com.hotelier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FoodOrderDetailsDTO {
    @NotBlank(message = "Item is required")
    private Long itemId;
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
    private String specialInstructions;
}
