package com.hotelier.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "hotel_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "important_info", columnDefinition = "TEXT")
    private String importantInfo;

    @Column(name = "check_in_time")
    private LocalTime checkInTime;

    @Column(name = "check_out_time")
    private LocalTime checkOutTime;

    @Column(name = "breakfast_time")
    private LocalTime breakfastTime;

    @Column(name = "lunch_time")
    private LocalTime lunchTime;

    @Column(name = "dinner_time")
    private LocalTime dinnerTime;

    @Column(name = "reception_hours")
    private String receptionHours;

    @Column(name = "google_map_url", columnDefinition = "TEXT")
    private String googleMapUrl;

    @Column(name = "wifi_password")
    private String wifiPassword;

    @Column(name = "wifi_network_name")
    private String wifiNetworkName;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

