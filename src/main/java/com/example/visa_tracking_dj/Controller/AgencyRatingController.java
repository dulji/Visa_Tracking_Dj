package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.AgencyRatingDto;
import com.example.visa_tracking_dj.Service.AgencyRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class AgencyRatingController {

    private final AgencyRatingService agencyRatingService;

    @PostMapping
    public ResponseEntity<AgencyRatingDto> createRating(@RequestBody AgencyRatingDto ratingDto) {
        AgencyRatingDto createdRating = agencyRatingService.createRating(ratingDto);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AgencyRatingDto>> getAllRatings(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "ratingId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        Page<AgencyRatingDto> ratings = agencyRatingService.getAllRatings(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyRatingDto> getRatingById(@PathVariable("id") Integer id) {
        AgencyRatingDto rating = agencyRatingService.getRatingById(id);
        return ResponseEntity.ok(rating);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgencyRatingDto> updateRating(
            @PathVariable("id") Integer id,
            @RequestBody AgencyRatingDto ratingDto
    ) {
        AgencyRatingDto updatedRating = agencyRatingService.updateRating(id, ratingDto);
        return ResponseEntity.ok(updatedRating);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable("id") Integer id) {
        agencyRatingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}
