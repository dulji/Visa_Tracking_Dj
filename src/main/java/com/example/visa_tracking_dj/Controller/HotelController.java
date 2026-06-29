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
    @PreAuthorize("hasAnyRole('HOTEL_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<Page<HotelDto>> getAllHotels(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
       return ResponseEntity.ok(hotelService.getAllHotels(pageNo, pageSize, sortBy, sortDir));

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HOTEL_STAFF', 'ADMIN', 'TOURIST_POLICE')")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable("id") Integer hotelId){
        return ResponseEntity.ok(hotelService.getHotelById(hotelId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<Page<HotelDto>> createHotel(
            @RequestBody HotelDto dto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            Sort sort){
        Page<HotelDto> updatedPage = hotelService.createHotel(dto, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(updatedPage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<Page<HotelDto>> updateHotel(
            @PathVariable("id") Integer hotelId,
            @RequestBody HotelDto dto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        Page<HotelDto> updatedPage = hotelService.updateHotel(hotelId, dto, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<Page<HotelDto>> patchComplaint(
            @PathVariable("id") Integer hotelId,
            @RequestBody HotelDto dto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        Page<HotelDto> updatedPage = hotelService.patchHotel(hotelId, dto, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HOTEL_STAFF', 'ADMIN')")
    public ResponseEntity<Page<HotelDto>> deleteHotel(
            @PathVariable("id") Integer hotelId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        Page<HotelDto> updatedPage = hotelService.deleteHotel(hotelId, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }
}
