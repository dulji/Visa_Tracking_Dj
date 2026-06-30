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

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    private HotelDto convertToDto(HotelEntity entity){
        return new HotelDto(
                entity.getHotelId(),
                entity.getHotelName(),
                entity.getRegistrationNumber()
        );
    }

    private HotelEntity convertToEntity(HotelDto dto){
        HotelEntity entity = new HotelEntity();
        entity.setHotelId(dto.getHotelId());
        entity.setHotelName(dto.getHotelName());
        entity.setRegistrationNumber(dto.getRegistrationNumber());
        return entity;
    }

    public Page<HotelDto> getAllHotels(int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return hotelRepository.findAll(pageable).map(this::convertToDto);

    }

    public HotelDto getHotelById(Integer hotelId){
        HotelEntity entity = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found with Id : " + hotelId));
        return convertToDto(entity);
    }

    public HotelDto createHotel(HotelDto dto){
        HotelEntity entity = convertToEntity(dto);
        entity.setHotelId(null);

        HotelEntity savedEntity = hotelRepository.save(entity);
        return convertToDto(savedEntity);
    }

    public HotelDto updateHotel(Integer hotelId, HotelDto dto){
        HotelEntity existingEntity = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found with Id : " + hotelId));

        existingEntity.setHotelName(dto.getHotelName());
        existingEntity.setRegistrationNumber(dto.getRegistrationNumber());

        HotelEntity savedEntity = hotelRepository.save(existingEntity);
        return convertToDto(savedEntity);

    }

    public HotelDto patchHotel(Integer hotelId, HotelDto dto){
        HotelEntity existingEntity = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Update cannot be done. Hotel not found with Id : " + hotelId));

        if(dto.getHotelName() != null){
            existingEntity.setHotelName(dto.getHotelName());
        }
        if(dto.getRegistrationNumber() != null){
            existingEntity.setRegistrationNumber(dto.getRegistrationNumber());
        }

        HotelEntity savedEntity = hotelRepository.save(existingEntity);
        return convertToDto(savedEntity);


    }

    public void deleteHotel(Integer hotelId){
        if(!hotelRepository.existsById(hotelId)){
            throw new RuntimeException("Deletion cannot be done. Hotel not found with ID : " + hotelId);
        }
        hotelRepository.deleteById(hotelId);
    }



}

