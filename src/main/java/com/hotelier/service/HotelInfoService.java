package com.hotelier.service;

import com.hotelier.dto.HotelInfoDTO;
import com.hotelier.model.HotelInfo;
import com.hotelier.repository.HotelInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelInfoService {
    @Autowired
    private HotelInfoRepository hotelInfoRepository;

    public HotelInfoDTO getHotelInfo() {
        List<HotelInfo> infoList = hotelInfoRepository.findAll();
        if (infoList.isEmpty()) {
            HotelInfo hotelInfo = new HotelInfo();
            hotelInfo = hotelInfoRepository.save(hotelInfo);
            return convertToDTO(hotelInfo);
        }
        return convertToDTO(infoList.get(0));
    }

    public HotelInfoDTO updateHotelInfo(HotelInfoDTO dto) {
        List<HotelInfo> infoList = hotelInfoRepository.findAll();
        HotelInfo hotelInfo;
        
        if (infoList.isEmpty()) {
            hotelInfo = new HotelInfo();
        } else {
            hotelInfo = infoList.get(0);
        }

        hotelInfo.setImportantInfo(dto.getImportantInfo());
        hotelInfo.setCheckInTime(dto.getCheckInTime());
        hotelInfo.setCheckOutTime(dto.getCheckOutTime());
        hotelInfo.setBreakfastTime(dto.getBreakfastTime());
        hotelInfo.setLunchTime(dto.getLunchTime());
        hotelInfo.setDinnerTime(dto.getDinnerTime());
        hotelInfo.setReceptionHours(dto.getReceptionHours());
        hotelInfo.setGoogleMapUrl(dto.getGoogleMapUrl());
        hotelInfo.setWifiPassword(dto.getWifiPassword());
        hotelInfo.setWifiNetworkName(dto.getWifiNetworkName());

        hotelInfo = hotelInfoRepository.save(hotelInfo);
        return convertToDTO(hotelInfo);
    }

    private HotelInfoDTO convertToDTO(HotelInfo hotelInfo) {
        HotelInfoDTO dto = new HotelInfoDTO();
        dto.setId(hotelInfo.getId());
        dto.setImportantInfo(hotelInfo.getImportantInfo());
        dto.setCheckInTime(hotelInfo.getCheckInTime());
        dto.setCheckOutTime(hotelInfo.getCheckOutTime());
        dto.setBreakfastTime(hotelInfo.getBreakfastTime());
        dto.setLunchTime(hotelInfo.getLunchTime());
        dto.setDinnerTime(hotelInfo.getDinnerTime());
        dto.setReceptionHours(hotelInfo.getReceptionHours());
        dto.setGoogleMapUrl(hotelInfo.getGoogleMapUrl());
        dto.setWifiPassword(hotelInfo.getWifiPassword());
        dto.setWifiNetworkName(hotelInfo.getWifiNetworkName());
        return dto;
    }
}

