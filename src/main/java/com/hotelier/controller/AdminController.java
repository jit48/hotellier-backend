package com.hotelier.controller;

import com.hotelier.dto.*;
import com.hotelier.model.FoodOrder;
import com.hotelier.model.PickupDrop;
import com.hotelier.model.RoomRequest;
import com.hotelier.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    private HotelInfoService hotelInfoService;

    @Autowired
    private FoodOrderService foodOrderService;

    @Autowired
    private RoomRequestService roomRequestService;

    @Autowired
    private PickupDropService pickupDropService;

    @Autowired
    private MenuService menuService;

    // Hotel Info Management
    @GetMapping("/hotel-info")
    public ResponseEntity<HotelInfoDTO> getHotelInfo() {
        return ResponseEntity.ok(hotelInfoService.getHotelInfo());
    }

    @PutMapping("/hotel-info")
    public ResponseEntity<HotelInfoDTO> updateHotelInfo(@Valid @RequestBody HotelInfoDTO dto) {
        return ResponseEntity.ok(hotelInfoService.updateHotelInfo(dto));
    }

    // Food Orders Management
    @GetMapping("/food-orders")
    public ResponseEntity<List<FoodOrderDTO>> getAllFoodOrders(
            @RequestParam(required = false) FoodOrder.OrderStatus status) {
        if (status != null) {
            return ResponseEntity.ok(foodOrderService.getOrdersByStatus(status));
        }
        return ResponseEntity.ok(foodOrderService.getAllOrders());
    }

    @GetMapping("/food-orders/{id}")
    public ResponseEntity<FoodOrderDTO> getFoodOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(foodOrderService.getOrderById(id));
    }

    @PutMapping("/food-orders/{id}/status")
    public ResponseEntity<FoodOrderDTO> updateFoodOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        FoodOrder.OrderStatus status = FoodOrder.OrderStatus.valueOf(request.get("status"));
        return ResponseEntity.ok(foodOrderService.updateOrderStatus(id, status));
    }

    // Room Requests Management
    @GetMapping("/room-requests")
    public ResponseEntity<List<RoomRequestDTO>> getAllRoomRequests(
            @RequestParam(required = false) RoomRequest.RequestStatus status) {
        if (status != null) {
            return ResponseEntity.ok(roomRequestService.getRequestsByStatus(status));
        }
        return ResponseEntity.ok(roomRequestService.getAllRequests());
    }

    @GetMapping("/room-requests/{id}")
    public ResponseEntity<RoomRequestDTO> getRoomRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(roomRequestService.getRequestById(id));
    }

    @PutMapping("/room-requests/{id}/status")
    public ResponseEntity<RoomRequestDTO> updateRoomRequestStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        RoomRequest.RequestStatus status = RoomRequest.RequestStatus.valueOf(request.get("status"));
        String adminNotes = request.get("adminNotes");
        return ResponseEntity.ok(roomRequestService.updateRequestStatus(id, status, adminNotes));
    }

    // Pickup/Drop Management
    @GetMapping("/pickup-drop")
    public ResponseEntity<List<PickupDropDTO>> getAllPickupDropRequests(
            @RequestParam(required = false) PickupDrop.RequestStatus status) {
        if (status != null) {
            return ResponseEntity.ok(pickupDropService.getRequestsByStatus(status));
        }
        return ResponseEntity.ok(pickupDropService.getAllRequests());
    }

    @GetMapping("/pickup-drop/{id}")
    public ResponseEntity<PickupDropDTO> getPickupDropById(@PathVariable Long id) {
        return ResponseEntity.ok(pickupDropService.getRequestById(id));
    }

    @PutMapping("/pickup-drop/{id}/status")
    public ResponseEntity<PickupDropDTO> updatePickupDropStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        PickupDrop.RequestStatus status = PickupDrop.RequestStatus.valueOf(request.get("status"));
        String adminNotes = request.get("adminNotes");
        return ResponseEntity.ok(pickupDropService.updateRequestStatus(id, status, adminNotes));
    }

    @PostMapping("/create-menu-item")
    public ResponseEntity<MenuItemDTO> createMenuItem(@Valid @RequestBody MenuItemDTO request) {
        MenuItemDTO response = menuService.createMenuItem(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-menu-items")
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        List<MenuItemDTO> items = menuService.getAllMenuItems();
        return ResponseEntity.ok(items);
    }
}

