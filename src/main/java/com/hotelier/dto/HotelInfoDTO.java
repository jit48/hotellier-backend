package com.hotelier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelInfoDTO {
    private Long id;
    private String importantInfo;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private LocalTime breakfastTime;
    private LocalTime lunchTime;
    private LocalTime dinnerTime;
    private String receptionHours;
    private String googleMapUrl;
    private String wifiPassword;
    private String wifiNetworkName;
}

