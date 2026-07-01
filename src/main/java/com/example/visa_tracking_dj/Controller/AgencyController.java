package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.AgencyDto;
import com.example.visa_tracking_dj.Entity.AgencyEntity;
import com.example.visa_tracking_dj.Service.AgencyService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/agency")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    public AgencyController(AgencyService agencyService){
        this.agencyService = agencyService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<AgencyDto> getAgencyById(@PathVariable("id") Integer agencyId){
        AgencyDto agencyDto = agencyService.getAgencyById(agencyId);
        return ResponseEntity.ok(agencyDto);
    }
    //ds


    @GetMapping
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN', 'TOURIST_POLICE')")
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
    @GetMapping("/{agencyId}/tourists")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<List<Long>> getAssignedTourists(@PathVariable("agencyId") Integer agencyId){
        return ResponseEntity.ok(agencyService.getTouristsByAgency(agencyId));
    }



    @PostMapping
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN')")
    public ResponseEntity<AgencyDto> createAgency(
            @RequestBody AgencyDto agencyDto){
        AgencyDto createdAgency = agencyService.createAgency(agencyDto);

        return new ResponseEntity<>(createdAgency, HttpStatus.CREATED);
    }

    @PostMapping("/{agencyId}/assign-tourist/{touristId}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN')")
    public ResponseEntity<String> assignTourist(
            @PathVariable("agencyId") Integer agencyId,
            @PathVariable("touristId") Long touristId){
        try {
            agencyService.assignTourist(agencyId, touristId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tourist successfully assigned to agency.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign tourist. Please check server logs.");
        }
    }

    @PutMapping("/{agencyId}/reassign-tourist/{touristId}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN')")
    public ResponseEntity<String> reassignTourist(
            @PathVariable("agencyId") Integer agencyId,
            @PathVariable("touristId") Long touristId){
        try {
            agencyService.reassignTourist(agencyId, touristId);
            return ResponseEntity.status(HttpStatus.OK).body("Tourist successfully reassigned to new agency.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reassign tourist. Please check server logs.");
        }
    }



    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN')")
    public ResponseEntity<AgencyDto> updateAgency(
            @PathVariable("id") Integer agencyId,
            @RequestBody AgencyDto agencyDto){
        AgencyDto updatedAgency = agencyService.updateAgency(agencyId, agencyDto);
        return ResponseEntity.ok(updatedAgency);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN')")
    public ResponseEntity<AgencyDto> partialUpdateAgency(
            @PathVariable("id") Integer agencyId,
            @RequestBody AgencyDto dto){
        AgencyDto updatedAgency = agencyService.partialUpdateAgency(agencyId, dto);
        return ResponseEntity.ok(updatedAgency);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN')")
    public ResponseEntity<Void> deleteAgency(
            @PathVariable("id") Integer agencyId){
        agencyService.deleteAgency(agencyId);
        return ResponseEntity.noContent().build();
   }
}


