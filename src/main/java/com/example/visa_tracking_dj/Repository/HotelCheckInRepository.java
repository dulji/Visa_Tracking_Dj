package com.example.visa_tracking_dj.Repository;

import com.example.visa_tracking_dj.Entity.HotelCheckInEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelCheckInRepository extends JpaRepository<HotelCheckInEntity, Integer> {
}
