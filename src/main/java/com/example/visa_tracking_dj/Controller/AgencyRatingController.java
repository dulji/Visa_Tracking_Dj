package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.AgencyRatingDto;
import com.example.visa_tracking_dj.Service.AgencyRatingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/rating")
public class AgencyRatingController {

    private final AgencyRatingService agencyRatingService;

    public AgencyRatingController(AgencyRatingService agencyRatingService){
        this.agencyRatingService = agencyRatingService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<Page<AgencyRatingDto>> getAllRatings(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "ratingId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){
        return ResponseEntity.ok(agencyRatingService.getAllRatings(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<AgencyRatingDto> getRatingById(@PathVariable Integer ratingId){
        return ResponseEntity.ok(agencyRatingService.getRatingById(ratingId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN')")
    public ResponseEntity<AgencyRatingDto> createRating(
            @RequestBody AgencyRatingDto dto){
        AgencyRatingDto createdRating = agencyRatingService.createRating(dto);

        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN')")
    public ResponseEntity<AgencyRatingDto> updateRating(
            @PathVariable Integer ratingId,
            @RequestBody AgencyRatingDto dto){
        AgencyRatingDto updatedRating = agencyRatingService.updateRating(ratingId, dto);
        return ResponseEntity.ok(updatedRating);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN')")
    public ResponseEntity<Page<AgencyRatingDto>> partialUpdateAgencyRating(
            @PathVariable Integer agencyRatingId,
            @RequestBody AgencyRatingDto dto){
        AgencyRatingDto updatedRating = agencyRatingService.partialUpdateAgencyRating(ratingId, dto);
        return ResponseEntity.ok(updatedRating);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENCY_STAFF', 'ADMIN')")
    public ResponseEntity<Void> deleteRating(
            @PathVariable Integer ratingId){
        agencyRatingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }

}

