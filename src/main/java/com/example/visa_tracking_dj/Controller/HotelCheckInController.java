package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.HotelCheckInDto;
import com.example.visa_tracking_dj.Service.HotelCheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkins")
@RequiredArgsConstructor
public class HotelCheckInController {

    private final HotelCheckInService hotelCheckInService;

    @PostMapping
    public ResponseEntity<HotelCheckInDto> createCheckIn(@RequestBody HotelCheckInDto checkInDto) {
        HotelCheckInDto createdCheckIn = hotelCheckInService.createCheckIn(checkInDto);
        return new ResponseEntity<>(createdCheckIn, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<HotelCheckInDto>> getAllCheckIns(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "checkinId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        Page<HotelCheckInDto> checkIns = hotelCheckInService.getAllCheckIns(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(checkIns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelCheckInDto> getCheckInById(@PathVariable("id") Integer id) {
        HotelCheckInDto checkIn = hotelCheckInService.getCheckInById(id);
        return ResponseEntity.ok(checkIn);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelCheckInDto> updateCheckIn(
            @PathVariable("id") Integer id,
            @RequestBody HotelCheckInDto checkInDto
    ) {
        HotelCheckInDto updatedCheckIn = hotelCheckInService.updateCheckIn(id, checkInDto);
        return ResponseEntity.ok(updatedCheckIn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckIn(@PathVariable("id") Integer id) {
        hotelCheckInService.deleteCheckIn(id);
        return ResponseEntity.noContent().build();
    }
}
