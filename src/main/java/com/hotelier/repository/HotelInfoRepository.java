package com.hotelier.repository;

import com.hotelier.model.HotelInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelInfoRepository extends JpaRepository<HotelInfo, Long> {
}

