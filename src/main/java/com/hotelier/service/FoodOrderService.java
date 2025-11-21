package com.hotelier.service;

import com.hotelier.dto.FoodOrderDTO;
import com.hotelier.dto.FoodOrderRequest;
import com.hotelier.model.FoodOrder;
import com.hotelier.model.User;
import com.hotelier.repository.FoodOrderRepository;
import com.hotelier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodOrderService {
    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private UserRepository userRepository;

    public FoodOrderDTO createOrder(String userEmail, FoodOrderRequest request) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        FoodOrder order = new FoodOrder();
        order.setUser(user);
        order.setItem(request.getItem());
        order.setDescription(request.getDescription());
        order.setQuantity(request.getQuantity());
        order.setSpecialInstructions(request.getSpecialInstructions());
        order.setRoomNumber(request.getRoomNumber() != null ? request.getRoomNumber() : user.getRoomNumber());
        order.setStatus(FoodOrder.OrderStatus.PENDING);

        order = foodOrderRepository.save(order);
        return convertToDTO(order);
    }

    public List<FoodOrderDTO> getUserOrders(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return foodOrderRepository.findByUser(user).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<FoodOrderDTO> getAllOrders() {
        return foodOrderRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<FoodOrderDTO> getOrdersByStatus(FoodOrder.OrderStatus status) {
        return foodOrderRepository.findByStatus(status).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public FoodOrderDTO updateOrderStatus(Long orderId, FoodOrder.OrderStatus status) {
        FoodOrder order = foodOrderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        if (status == FoodOrder.OrderStatus.DELIVERED) {
            order.setDeliveryTime(java.time.LocalDateTime.now());
        }

        order = foodOrderRepository.save(order);
        return convertToDTO(order);
    }

    public FoodOrderDTO getOrderById(Long orderId) {
        FoodOrder order = foodOrderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    private FoodOrderDTO convertToDTO(FoodOrder order) {
        FoodOrderDTO dto = new FoodOrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setUserEmail(order.getUser().getEmail());
        dto.setUserName(order.getUser().getName());
        dto.setItem(order.getItem());
        dto.setDescription(order.getDescription());
        dto.setQuantity(order.getQuantity());
        dto.setSpecialInstructions(order.getSpecialInstructions());
        dto.setStatus(order.getStatus());
        dto.setOrderTime(order.getOrderTime());
        dto.setDeliveryTime(order.getDeliveryTime());
        dto.setRoomNumber(order.getRoomNumber());
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }
}

