package com.hotelier.repository;

import com.hotelier.model.PickupDrop;
import com.hotelier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickupDropRepository extends JpaRepository<PickupDrop, Long> {
    List<PickupDrop> findByUser(User user);
    List<PickupDrop> findByStatus(PickupDrop.RequestStatus status);
}

