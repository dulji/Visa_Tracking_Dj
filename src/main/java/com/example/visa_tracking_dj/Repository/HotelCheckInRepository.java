package com.example.visa_tracking_dj.Repository;

import com.example.visa_tracking_dj.Entity.HotelCheckInEntity;
import com.example.visa_tracking_dj.Entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelCheckInRepository extends JpaRepository<HotelCheckInEntity, Integer> {

    List<HotelCheckInEntity> findByHotelId(HotelEntity hotel);

    boolean existsByTouristId(Long touristId);
}
