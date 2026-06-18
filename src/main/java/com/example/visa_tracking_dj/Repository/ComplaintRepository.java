package com.example.visa_tracking_dj.Repository;

import com.example.visa_tracking_dj.Entity.ComplaintEntity;
import com.example.visa_tracking_dj.Service.ComplaintService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<ComplaintEntity, Integer> {
}
