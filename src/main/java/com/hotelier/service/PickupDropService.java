package com.hotelier.service;

import com.hotelier.dto.PickupDropCreate;
import com.hotelier.dto.PickupDropDTO;
import com.hotelier.model.PickupDrop;
import com.hotelier.model.User;
import com.hotelier.repository.PickupDropRepository;
import com.hotelier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PickupDropService {
    @Autowired
    private PickupDropRepository pickupDropRepository;

    @Autowired
    private UserRepository userRepository;

    public PickupDropDTO createRequest(String userEmail, PickupDropCreate request) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        PickupDrop pickupDrop = new PickupDrop();
        pickupDrop.setUser(user);
        pickupDrop.setServiceType(request.getServiceType());
        pickupDrop.setLocation(request.getLocation());
        pickupDrop.setPickupDateTime(request.getPickupDateTime());
        pickupDrop.setDropDateTime(request.getDropDateTime());
        pickupDrop.setNumberOfPassengers(request.getNumberOfPassengers());
        pickupDrop.setFlightNumber(request.getFlightNumber());
        pickupDrop.setSpecialInstructions(request.getSpecialInstructions());
        pickupDrop.setRoomNumber(request.getRoomNumber() != null ? request.getRoomNumber() : user.getRoomNumber());
        pickupDrop.setContactPhone(request.getContactPhone() != null ? request.getContactPhone() : user.getPhoneNumber());
        pickupDrop.setStatus(PickupDrop.RequestStatus.PENDING);

        pickupDrop = pickupDropRepository.save(pickupDrop);
        return convertToDTO(pickupDrop);
    }

    public List<PickupDropDTO> getUserRequests(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return pickupDropRepository.findByUser(user).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<PickupDropDTO> getAllRequests() {
        return pickupDropRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<PickupDropDTO> getRequestsByStatus(PickupDrop.RequestStatus status) {
        return pickupDropRepository.findByStatus(status).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public PickupDropDTO updateRequestStatus(Long requestId, PickupDrop.RequestStatus status, String adminNotes) {
        PickupDrop request = pickupDropRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(status);
        if (adminNotes != null) {
            request.setAdminNotes(adminNotes);
        }

        request = pickupDropRepository.save(request);
        return convertToDTO(request);
    }

    public PickupDropDTO getRequestById(Long requestId) {
        PickupDrop request = pickupDropRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        return convertToDTO(request);
    }

    private PickupDropDTO convertToDTO(PickupDrop pickupDrop) {
        PickupDropDTO dto = new PickupDropDTO();
        dto.setId(pickupDrop.getId());
        dto.setUserId(pickupDrop.getUser().getId());
        dto.setUserEmail(pickupDrop.getUser().getEmail());
        dto.setUserName(pickupDrop.getUser().getName());
        dto.setServiceType(pickupDrop.getServiceType());
        dto.setLocation(pickupDrop.getLocation());
        dto.setPickupDateTime(pickupDrop.getPickupDateTime());
        dto.setDropDateTime(pickupDrop.getDropDateTime());
        dto.setNumberOfPassengers(pickupDrop.getNumberOfPassengers());
        dto.setFlightNumber(pickupDrop.getFlightNumber());
        dto.setSpecialInstructions(pickupDrop.getSpecialInstructions());
        dto.setStatus(pickupDrop.getStatus());
        dto.setRoomNumber(pickupDrop.getRoomNumber());
        dto.setContactPhone(pickupDrop.getContactPhone());
        dto.setAdminNotes(pickupDrop.getAdminNotes());
        dto.setCreatedAt(pickupDrop.getCreatedAt());
        dto.setUpdatedAt(pickupDrop.getUpdatedAt());
        return dto;
    }
}

