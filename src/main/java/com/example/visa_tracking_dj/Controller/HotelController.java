package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.HotelDto;
import com.example.visa_tracking_dj.Service.HotelService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<Page<HotelDto>> getAllHotels(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
       return ResponseEntity.ok(hotelService.getAllHotels(pageNo, pageSize, sortBy, sortDir));

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable("id") Integer hotelId){
        return ResponseEntity.ok(hotelService.getHotelById(hotelId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto dto){
       HotelDto createdHotel = hotelService.createHotel(dto);
       return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable("id") Integer hotelId, @RequestBody HotelDto dto){
        HotelDto updatedHotel = hotelService.updateHotel(hotelId, dto);
        return ResponseEntity.ok(updatedHotel);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<HotelDto> patchHotel(
            @PathVariable("id") Integer hotelId,
            @RequestBody HotelDto dto){
       HotelDto updatedHotel = hotelService.patchHotel(hotelId, dto);
       return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('IMMIGRATION_OFFICER', 'HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") Integer hotelId){
        hotelService.deleteHotel(hotelId);
        return ResponseEntity.noContent().build();
    }
}
