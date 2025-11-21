package com.hotelier.dto;

import com.hotelier.model.RoomRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDTO {
    private Long id;
    private Long userId;
    private String userEmail;
    private String userName;
    private RoomRequest.RequestType requestType;
    private String description;
    private RoomRequest.RequestStatus status;
    private String roomNumber;
    private String adminNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

