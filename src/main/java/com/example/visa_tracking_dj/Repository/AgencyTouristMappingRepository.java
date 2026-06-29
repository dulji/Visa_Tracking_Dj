package com.example.visa_tracking_dj.Repository;

import com.example.visa_tracking_dj.Entity.AgencyEntity;
import com.example.visa_tracking_dj.Entity.AgencyTouristMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgencyTouristMappingRepository extends JpaRepository<AgencyTouristMappingEntity, Integer> {

    List<AgencyTouristMappingEntity> findByAgency(AgencyEntity agency);

    boolean existsByTouristId(Long touristId);
    
    Optional<AgencyTouristMappingEntity> findByTouristId(Long touristId);
}
