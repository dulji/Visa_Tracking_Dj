package com.example.visa_tracking_dj.Repository;

import com.example.visa_tracking_dj.Entity.HotelBookingMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelBookingRepository extends JpaRepository<HotelBookingMappingEntity, Integer> {

}
