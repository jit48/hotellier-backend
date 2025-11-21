package com.hotelier.dto;

import com.hotelier.model.PickupDrop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickupDropDTO {
    private Long id;
    private Long userId;
    private String userEmail;
    private String userName;
    private PickupDrop.ServiceType serviceType;
    private String location;
    private LocalDateTime pickupDateTime;
    private LocalDateTime dropDateTime;
    private Integer numberOfPassengers;
    private String flightNumber;
    private String specialInstructions;
    private PickupDrop.RequestStatus status;
    private String roomNumber;
    private String contactPhone;
    private String adminNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

