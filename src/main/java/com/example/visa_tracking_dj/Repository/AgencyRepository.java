package com.example.visa_tracking_dj.Repository;

import com.example.visa_tracking_dj.Entity.AgencyEntity;
import com.example.visa_tracking_dj.Service.AgencyService;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AgencyRepository extends JpaRepository<AgencyEntity, Integer> {}
