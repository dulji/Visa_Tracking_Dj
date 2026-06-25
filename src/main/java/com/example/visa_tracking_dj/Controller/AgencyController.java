package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.AgencyDto;
import com.example.visa_tracking_dj.Entity.AgencyEntity;
import com.example.visa_tracking_dj.Service.AgencyService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agency")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    public AgencyController(AgencyService agencyService){
        this.agencyService = agencyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyDto> getAgencyByIs(@PathVariable("id") Integer agencyId){
        AgencyDto agencyDto = agencyService.getAgencyById(agencyId);
        return ResponseEntity.ok(agencyDto);
    }
    //ds


    @GetMapping
    public ResponseEntity<Page<AgencyDto>> getAllAgencies(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "agencyName", required = false)String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false)String sortDir

    ){
        Page<AgencyDto> agenciesPage = agencyService.getAllAgencies(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(agenciesPage);
    }
    //Changes



    @PostMapping
    public ResponseEntity<Page<AgencyDto>> createAgency(
            @RequestBody AgencyDto agencyDto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue="agencyName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){
        Page<AgencyDto> updatedAgenciesPage = agencyService.createAgency(agencyDto, pageNo, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(updatedAgenciesPage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Page<AgencyDto>> updateAgency(
            @PathVariable("id") Integer agencyId,
            @RequestBody AgencyDto agencyDto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "agencyName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){
        Page<AgencyDto> updatedPage = agencyService.updateAgency(agencyId, agencyDto, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Page<AgencyDto>> deleteAgency(
            @PathVariable("id") Integer agencyId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "agencyName", required = false)String sortBy,
            @RequestParam(value="sortDir", defaultValue="asc", required = false)String sortDir
    ){
        Page<AgencyDto> updatedPage = agencyService.deleteAgency(agencyId, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
   }
}


