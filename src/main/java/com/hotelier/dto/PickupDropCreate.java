package com.hotelier.dto;

import com.hotelier.model.PickupDrop;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PickupDropCreate {
    @NotNull(message = "Service type is required")
    private PickupDrop.ServiceType serviceType;

    @NotBlank(message = "Location is required")
    private String location;

    private LocalDateTime pickupDateTime;
    private LocalDateTime dropDateTime;
    private Integer numberOfPassengers;
    private String flightNumber;
    private String specialInstructions;
    private String roomNumber;
    private String contactPhone;
}

