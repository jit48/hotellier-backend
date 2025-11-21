package com.hotelier.repository;

import com.hotelier.model.FoodOrder;
import com.hotelier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
    List<FoodOrder> findByUser(User user);
    List<FoodOrder> findByStatus(FoodOrder.OrderStatus status);
}

