package com.hotelier.controller;

import com.hotelier.dto.*;
import com.hotelier.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guest")
@CrossOrigin(origins = "*")
public class GuestController {
    @Autowired
    private HotelInfoService hotelInfoService;

    @Autowired
    private FoodOrderService foodOrderService;

    @Autowired
    private RoomRequestService roomRequestService;

    @Autowired
    private PickupDropService pickupDropService;

    // Hotel Info
    @GetMapping("/hotel-info")
    public ResponseEntity<HotelInfoDTO> getHotelInfo() {
        return ResponseEntity.ok(hotelInfoService.getHotelInfo());
    }

    // Food Orders
    @PostMapping("/food-orders")
    public ResponseEntity<FoodOrderDTO> createFoodOrder(
            Authentication authentication,
            @Valid @RequestBody FoodOrderRequest request) {
        String email = authentication.getName();
        return ResponseEntity.ok(foodOrderService.createOrder(email, request));
    }

    @GetMapping("/food-orders")
    public ResponseEntity<List<FoodOrderDTO>> getMyFoodOrders(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(foodOrderService.getUserOrders(email));
    }

    @GetMapping("/food-orders/{id}")
    public ResponseEntity<FoodOrderDTO> getFoodOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(foodOrderService.getOrderById(id));
    }

    // Room Requests
    @PostMapping("/room-requests")
    public ResponseEntity<RoomRequestDTO> createRoomRequest(
            Authentication authentication,
            @Valid @RequestBody RoomRequestCreate request) {
        String email = authentication.getName();
        return ResponseEntity.ok(roomRequestService.createRequest(email, request));
    }

    @GetMapping("/room-requests")
    public ResponseEntity<List<RoomRequestDTO>> getMyRoomRequests(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(roomRequestService.getUserRequests(email));
    }

    @GetMapping("/room-requests/{id}")
    public ResponseEntity<RoomRequestDTO> getRoomRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(roomRequestService.getRequestById(id));
    }

    // Pickup/Drop
    @PostMapping("/pickup-drop")
    public ResponseEntity<PickupDropDTO> createPickupDrop(
            Authentication authentication,
            @Valid @RequestBody PickupDropCreate request) {
        String email = authentication.getName();
        return ResponseEntity.ok(pickupDropService.createRequest(email, request));
    }

    @GetMapping("/pickup-drop")
    public ResponseEntity<List<PickupDropDTO>> getMyPickupDropRequests(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(pickupDropService.getUserRequests(email));
    }

    @GetMapping("/pickup-drop/{id}")
    public ResponseEntity<PickupDropDTO> getPickupDropById(@PathVariable Long id) {
        return ResponseEntity.ok(pickupDropService.getRequestById(id));
    }
}

