package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.HotelDto;
import com.example.visa_tracking_dj.Service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        HotelDto createdHotel = hotelService.createHotel(hotelDto);
        return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<HotelDto>> getAllHotels(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hotelId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        Page<HotelDto> hotels = hotelService.getAllHotels(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable("id") Integer id) {
        HotelDto hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotel(
            @PathVariable("id") Integer id,
            @RequestBody HotelDto hotelDto
    ) {
        HotelDto updatedHotel = hotelService.updateHotel(id, hotelDto);
        return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") Integer id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}
