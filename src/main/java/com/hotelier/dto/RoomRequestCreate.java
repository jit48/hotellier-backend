package com.hotelier.dto;

import com.hotelier.model.RoomRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomRequestCreate {
    @NotNull(message = "Request type is required")
    private RoomRequest.RequestType requestType;

    @NotBlank(message = "Description is required")
    private String description;

    private String roomNumber;
}

