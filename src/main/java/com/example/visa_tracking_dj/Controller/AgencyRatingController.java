package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.AgencyRatingDto;
import com.example.visa_tracking_dj.Service.AgencyRatingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class AgencyRatingController {

    private final AgencyRatingService agencyRatingService;

    public AgencyRatingController(AgencyRatingService agencyRatingService){
        this.agencyRatingService = agencyRatingService;
    }

    @GetMapping
    public ResponseEntity<Page<AgencyRatingDto>> getAllRatings(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "ratingId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){
        return ResponseEntity.ok(agencyRatingService.getAllRatings(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyRatingDto> getRatingById(@PathVariable Integer ratingId){
        return ResponseEntity.ok(agencyRatingService.getRatingById(ratingId));
    }

    @PostMapping
    public ResponseEntity<Page<AgencyRatingDto>> createRating(
            @RequestBody AgencyRatingDto dto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "ratingId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){
        Page<AgencyRatingDto> updatedPage = agencyRatingService.createRating(dto, pageNo, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(updatedPage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Page<AgencyRatingDto>> updateRating(
            @PathVariable Integer ratingId,
            @RequestBody AgencyRatingDto dto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "ratingId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){
        Page<AgencyRatingDto> updatedPage = agencyRatingService.updateRating(ratingId, dto, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Page<AgencyRatingDto>> deleteRating(
            @PathVariable Integer ratingId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "ratingId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){
        Page<AgencyRatingDto> updatedPage = agencyRatingService.deleteRating(ratingId, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }

}

