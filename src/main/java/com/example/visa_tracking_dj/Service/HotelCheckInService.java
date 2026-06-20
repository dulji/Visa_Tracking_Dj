package com.example.visa_tracking_dj.Service;

import com.example.visa_tracking_dj.Dto.HotelCheckInDto;
import com.example.visa_tracking_dj.Entity.HotelCheckInEntity;
import com.example.visa_tracking_dj.Entity.HotelEntity;
import com.example.visa_tracking_dj.Repository.HotelCheckInRepository;
import com.example.visa_tracking_dj.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class HotelCheckInService {

    private final HotelCheckInRepository hotelCheckInRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelCheckInService(HotelCheckInRepository hotelCheckInRepository, HotelRepository hotelRepository) {
        this.hotelCheckInRepository = hotelCheckInRepository;
        this.hotelRepository = hotelRepository;
    }

    private HotelCheckInDto convertToDto(HotelCheckInEntity entity) {
        return new HotelCheckInDto(
                entity.getCheckinId(),
                entity.getHotelId() != null ? entity.getHotelId().getHotelId() : null,
                entity.getCheckInDate()
        );
    }

    private HotelCheckInEntity convertToEntity(HotelCheckInDto dto) {
        HotelCheckInEntity entity = new HotelCheckInEntity();
        entity.setCheckinId(dto.getCheckinId());

        if (dto.getHotelId() != null) {
            HotelEntity hotel = hotelRepository.findById(dto.getHotelId())
                    .orElseThrow(() -> new NoSuchElementException("Hotel not found with ID: " + dto.getHotelId()));
            entity.setHotelId(hotel);
        }

        entity.setCheckInDate(dto.getCheckInDate());
        return entity;
    }

    public Page<HotelCheckInDto> getAllCheckIns(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<HotelCheckInEntity> checkinsPage = hotelCheckInRepository.findAll(pageable);
        return checkinsPage.map(this::convertToDto);
    }

    public HotelCheckInDto getCheckInById(Integer checkinId) {
        HotelCheckInEntity entity = hotelCheckInRepository.findById(checkinId)
                .orElseThrow(() -> new NoSuchElementException("Check-in record not found with ID: " + checkinId));
        return convertToDto(entity);
    }

    public HotelCheckInDto createCheckIn(HotelCheckInDto checkInDto) {
        HotelCheckInEntity entityToSave = convertToEntity(checkInDto);
        HotelCheckInEntity savedEntity = hotelCheckInRepository.save(entityToSave);
        return convertToDto(savedEntity);
    }

    public HotelCheckInDto updateCheckIn(Integer checkinId, HotelCheckInDto checkInDto) {
        HotelCheckInEntity existingEntity = hotelCheckInRepository.findById(checkinId)
                .orElseThrow(() -> new NoSuchElementException("Check-in record not found with ID: " + checkinId));

        if (checkInDto.getHotelId() != null) {
            HotelEntity hotel = hotelRepository.findById(checkInDto.getHotelId())
                    .orElseThrow(() -> new NoSuchElementException("Hotel not found with ID: " + checkInDto.getHotelId()));
            existingEntity.setHotelId(hotel);
        } else {
            existingEntity.setHotelId(null);
        }

        existingEntity.setCheckInDate(checkInDto.getCheckInDate());

        HotelCheckInEntity updatedEntity = hotelCheckInRepository.save(existingEntity);
        return convertToDto(updatedEntity);
    }

    public void deleteCheckIn(Integer checkinId) {
        if (!hotelCheckInRepository.existsById(checkinId)) {
            throw new NoSuchElementException("Check-in record not found with ID: " + checkinId);
        }
        hotelCheckInRepository.deleteById(checkinId);
    }
}
