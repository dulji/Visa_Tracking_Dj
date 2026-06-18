package com.example.visa_tracking_dj.Repository;

import com.example.visa_tracking_dj.Entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity, Integer> {
}
