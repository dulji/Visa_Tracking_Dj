package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Entity.AgencyEntity;
import com.example.visa_tracking_dj.Service.AgencyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class AgencyController {

    private final AgencyService agencyService;


    @PostMapping
    public ResponseEntity<Page<AgencyEntity>> createAgency(
            @RequestBody AgencyEntity agencyEntity,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue="agencyName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){
        Page<AgencyEntity> updatedAgenciesPage = agencyService.createAgency(agencyEntity, pageNo, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(updatedAgenciesPage, HttpStatus.CREATED);
    }
}

}
