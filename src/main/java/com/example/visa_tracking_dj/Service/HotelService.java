package com.example.visa_tracking_dj.Service;

import com.example.visa_tracking_dj.Dto.HotelDto;
import com.example.visa_tracking_dj.Entity.HotelEntity;
import com.example.visa_tracking_dj.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    private HotelDto convertToDto(HotelEntity entity) {
        return new HotelDto(
                entity.getHotelId(),
                entity.getRegistrationNumber()
        );
    }

    private HotelEntity convertToEntity(HotelDto dto) {
        HotelEntity entity = new HotelEntity();
        entity.setHotelId(dto.getHotelId());
        entity.setRegistrationNumber(dto.getRegistrationNumber());
        return entity;
    }

    public Page<HotelDto> getAllHotels(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<HotelEntity> hotelsPage = hotelRepository.findAll(pageable);
        return hotelsPage.map(this::convertToDto);
    }

    public HotelDto getHotelById(Integer hotelId) {
        HotelEntity entity = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new NoSuchElementException("Hotel not found with ID: " + hotelId));
        return convertToDto(entity);
    }

    public HotelDto createHotel(HotelDto hotelDto) {
        HotelEntity entityToSave = convertToEntity(hotelDto);
        HotelEntity savedEntity = hotelRepository.save(entityToSave);
        return convertToDto(savedEntity);
    }

    public HotelDto updateHotel(Integer hotelId, HotelDto hotelDto) {
        HotelEntity existingEntity = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new NoSuchElementException("Hotel not found with ID: " + hotelId));

        existingEntity.setRegistrationNumber(hotelDto.getRegistrationNumber());

        HotelEntity updatedEntity = hotelRepository.save(existingEntity);
        return convertToDto(updatedEntity);
    }

    public void deleteHotel(Integer hotelId) {
        if (!hotelRepository.existsById(hotelId)) {
            throw new NoSuchElementException("Hotel not found with ID: " + hotelId);
        }
        hotelRepository.deleteById(hotelId);
    }
}
