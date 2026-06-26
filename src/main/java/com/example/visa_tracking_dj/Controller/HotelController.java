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

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public ResponseEntity<Page<HotelDto>> getAllHotels(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
       return ResponseEntity.ok(hotelService.getAllHotels(pageNo, pageSize, sortBy, sortDir));

    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Integer hotelId){
        return ResponseEntity.ok(hotelService.getHotelById(hotelId));
    }

    @PostMapping
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
    public ResponseEntity<Page<HotelDto>> updateHotel(
            @PathVariable Integer hotelId,
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
    public ResponseEntity<Page<HotelDto>> patchComplaint(
            @PathVariable Integer hotelId,
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
    public ResponseEntity<Page<HotelDto>> deleteHotel(
            @PathVariable Integer hotelId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        Page<HotelDto> updatedPage = hotelService.deleteHotel(hotelId, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }
}
