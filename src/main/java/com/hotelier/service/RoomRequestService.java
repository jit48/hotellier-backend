package com.hotelier.service;

import com.hotelier.dto.RoomRequestCreate;
import com.hotelier.dto.RoomRequestDTO;
import com.hotelier.model.RoomRequest;
import com.hotelier.model.User;
import com.hotelier.repository.RoomRequestRepository;
import com.hotelier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomRequestService {
    @Autowired
    private RoomRequestRepository roomRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public RoomRequestDTO createRequest(String userEmail, RoomRequestCreate request) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        RoomRequest roomRequest = new RoomRequest();
        roomRequest.setUser(user);
        roomRequest.setRequestType(request.getRequestType());
        roomRequest.setDescription(request.getDescription());
        roomRequest.setRoomNumber(request.getRoomNumber() != null ? request.getRoomNumber() : user.getRoomNumber());
        roomRequest.setStatus(RoomRequest.RequestStatus.PENDING);

        roomRequest = roomRequestRepository.save(roomRequest);
        return convertToDTO(roomRequest);
    }

    public List<RoomRequestDTO> getUserRequests(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return roomRequestRepository.findByUser(user).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<RoomRequestDTO> getAllRequests() {
        return roomRequestRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<RoomRequestDTO> getRequestsByStatus(RoomRequest.RequestStatus status) {
        return roomRequestRepository.findByStatus(status).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public RoomRequestDTO updateRequestStatus(Long requestId, RoomRequest.RequestStatus status, String adminNotes) {
        RoomRequest request = roomRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(status);
        if (adminNotes != null) {
            request.setAdminNotes(adminNotes);
        }

        request = roomRequestRepository.save(request);
        return convertToDTO(request);
    }

    public RoomRequestDTO getRequestById(Long requestId) {
        RoomRequest request = roomRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        return convertToDTO(request);
    }

    private RoomRequestDTO convertToDTO(RoomRequest request) {
        RoomRequestDTO dto = new RoomRequestDTO();
        dto.setId(request.getId());
        dto.setUserId(request.getUser().getId());
        dto.setUserEmail(request.getUser().getEmail());
        dto.setUserName(request.getUser().getName());
        dto.setRequestType(request.getRequestType());
        dto.setDescription(request.getDescription());
        dto.setStatus(request.getStatus());
        dto.setRoomNumber(request.getRoomNumber());
        dto.setAdminNotes(request.getAdminNotes());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUpdatedAt(request.getUpdatedAt());
        return dto;
    }
}

