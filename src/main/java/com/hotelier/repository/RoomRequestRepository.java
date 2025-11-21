package com.hotelier.repository;

import com.hotelier.model.RoomRequest;
import com.hotelier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRequestRepository extends JpaRepository<RoomRequest, Long> {
    List<RoomRequest> findByUser(User user);
    List<RoomRequest> findByStatus(RoomRequest.RequestStatus status);
}

