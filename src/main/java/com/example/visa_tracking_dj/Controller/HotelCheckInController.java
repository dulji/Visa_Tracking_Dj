package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.HotelCheckInDto;
import com.example.visa_tracking_dj.Dto.HotelDto;
import com.example.visa_tracking_dj.Entity.HotelCheckInEntity;
import com.example.visa_tracking_dj.Service.HotelCheckInService;
import com.example.visa_tracking_dj.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotelCheckIn")
public class HotelCheckInController {

    @Autowired
    private HotelCheckInService hotelCheckInService;

    @Autowired
    private HotelService hotelService;

    @GetMapping
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<Page<HotelCheckInDto>> getAllHotelCheckIns(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelCheckInId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return ResponseEntity.ok(hotelCheckInService.getAllHotelCheckIns(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<HotelCheckInDto> getHotelCheckInById(@PathVariable("id") Integer hotelCheckInId){
        return ResponseEntity.ok(hotelCheckInService.getHotelCheckInById(hotelCheckInId));
    }

    @GetMapping("/{hotelId}/tourists")
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<List<Long>> getCheckedInTourists(@PathVariable Integer hotelId){
        return  ResponseEntity.ok(hotelCheckInService.getTouristIdsByHotel(hotelId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<HotelCheckInDto> createHotelCheckIn(
            @RequestBody HotelCheckInDto dto){
        HotelCheckInDto createdCheckIn = hotelCheckInService.createHotelCheckIn(dto);
        return new ResponseEntity<>(createdCheckIn, HttpStatus.CREATED);

    }

    @PostMapping("/{hotelId}/assign-tourist/{touristId}")
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<String> assignTourist(
            @PathVariable Integer hotelId,
            @PathVariable Long touristId){
        hotelCheckInService.assignTouristToHotel(hotelId, touristId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tourist registered to hotel successfully.");
    }




    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<HotelCheckInDto> updateHotelCheckIn(
            @PathVariable Integer hotelCheckInId,
            @RequestBody HotelCheckInDto dto){
        HotelCheckInDto updatedCheckIn = hotelCheckInService.updateHotelCheckIn(hotelCheckInId, dto);
        return ResponseEntity.ok(updatedCheckIn);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<HotelCheckInDto> partialUpdateCheckIn(
            @PathVariable Integer hotelCheckInId,
            @RequestBody HotelCheckInDto dto){
        HotelCheckInDto updatedCheckIn = hotelCheckInService.partialUpdateHotelCheckIn(hotelCheckInId, dto);
        return ResponseEntity.ok(updatedCheckIn);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<Void> deleteCheckIn(
            @PathVariable Integer hotelCheckInId){
        hotelCheckInService.deleteCheckIn(hotelCheckInId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tourist/{touristId}/history")
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<List<com.example.visa_tracking_dj.Dto.TouristTravelLogDto>> getTouristTravelHistory(@PathVariable Long touristId) {
        return ResponseEntity.ok(hotelCheckInService.getTouristTravelHistory(touristId));
    }
}
