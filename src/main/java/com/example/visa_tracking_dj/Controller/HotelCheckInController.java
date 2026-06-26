package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.HotelCheckInDto;
import com.example.visa_tracking_dj.Dto.HotelDto;
import com.example.visa_tracking_dj.Service.HotelCheckInService;
import com.example.visa_tracking_dj.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hotelCheckIn")
public class HotelCheckInController {

    @Autowired
    private HotelCheckInService hotelCheckInService;

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public ResponseEntity<Page<HotelCheckInDto>> getAllHotelCheckIns(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelCheckInId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return ResponseEntity.ok(hotelCheckInService.getAllHotelCheckIns(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelCheckInDto> getHotelCheckInById(@PathVariable Integer hotelCheckInId){
        return ResponseEntity.ok(hotelCheckInService.getHotelCheckInById(hotelCheckInId));
    }

    @PostMapping
    public ResponseEntity<Page<HotelCheckInDto>> createHotelCheckIn(
            @RequestBody HotelCheckInDto dto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelCheckInId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        Page<HotelCheckInDto> updatedPage = hotelCheckInService.createHotelCheckIn(dto, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(updatedPage, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Page<HotelCheckInDto>> updateHotelCheckIn(
            @PathVariable Integer hotelCheckInId,
            @RequestBody HotelCheckInDto dto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelCheckInId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        Page<HotelCheckInDto> updatedPage = hotelCheckInService.updateHotelCheckIn(hotelCheckInId, dto, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Page<HotelCheckInDto>> partialUpdateCheckIn(
            @PathVariable Integer hotelCheckInId,
            @RequestBody HotelCheckInDto dto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelCheckInId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        Page<HotelCheckInDto> updatedPage = hotelCheckInService.partialUpdateHotelCheckIn(hotelCheckInId, dto, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Page<HotelCheckInDto>> deleteCheckIn(
            @PathVariable Integer hotelCheckInId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelCheckInId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        Page<HotelCheckInDto> updatedPage = hotelCheckInService.deleteCheckIn(hotelCheckInId, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }


}
