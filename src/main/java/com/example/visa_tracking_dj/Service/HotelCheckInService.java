package com.example.visa_tracking_dj.Service;

import com.example.visa_tracking_dj.Dto.HotelCheckInDto;
import com.example.visa_tracking_dj.Entity.HotelCheckInEntity;
import com.example.visa_tracking_dj.Entity.HotelEntity;
import com.example.visa_tracking_dj.Repository.HotelCheckInRepository;
import com.example.visa_tracking_dj.Repository.HotelRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelCheckInService {

    @Autowired
    private HotelCheckInRepository hotelCheckInRepository;

    @Autowired
    private HotelRepository hotelRepository;

    private HotelCheckInDto convertToDto(HotelCheckInEntity entity){
        return new HotelCheckInDto(
                entity.getCheckinId(),
                entity.getHotelId() != null ? entity.getHotelId().getHotelId() : null,
                entity.getTouristId(),
                entity.getCheckInDate()
        );
    }

    private HotelCheckInEntity convertToEntity(HotelCheckInDto dto){
        HotelCheckInEntity entity = new HotelCheckInEntity();
        entity.setCheckinId(dto.getCheckinId());
        entity.setCheckInDate(dto.getCheckInDate());

        if(dto.getHotelId() != null){
            HotelEntity hotel = hotelRepository.findById(dto.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Record of Hotel not found with Id : " + dto.getHotelId() ));
            entity.setHotelId(hotel);
        }
        try {
            entity.setTouristId(dto.getTouristId());
        }catch(Exception e){
            throw new RuntimeException("Record of tourist not found with Id : " + dto.getTouristId());
        }
        return entity;

    }

    public Page<HotelCheckInDto> getAllHotelCheckIns(int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return hotelCheckInRepository.findAll(pageable).map(this::convertToDto);
    }

    public HotelCheckInDto getHotelCheckInById(Integer hotelCheckInId){
        HotelCheckInEntity entity = hotelCheckInRepository.findById(hotelCheckInId).orElseThrow(() -> new RuntimeException("Hotel CheckIn not found with Id : " + hotelCheckInId));
        return convertToDto(entity);
    }

    public Page<HotelCheckInDto> createHotelCheckIn(HotelCheckInDto dto, int pageNo, int pageSize, String sortBy, String sortDir){
        HotelCheckInEntity entity = convertToEntity(dto);
        entity.setCheckinId(null);
        hotelCheckInRepository.save(entity);
        return getAllHotelCheckIns(pageNo, pageSize, sortBy, sortDir);

    }

    public void assignTouristToHotel(Integer hotelId, Long touristId){
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel record cannot be found with Id : " + hotelId));

        if(hotelCheckInRepository.existsByTouristId(touristId)){
            throw new RuntimeException("This tourist is already checked into a hotel room.");
        }

        HotelCheckInEntity checkIn = new HotelCheckInEntity();
        checkIn.setCheckinId(null);
        checkIn.setHotelId(hotel);
        checkIn.setTouristId(touristId);
        checkIn.setCheckInDate(new java.util.Date());

        hotelCheckInRepository.save(checkIn);
    }

    public List<Long> getTouristIdsByHotel(Integer hotelId){
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hote record not found with Id: " + hotelId));

        return hotelCheckInRepository.findByHotelId(hotel).stream()
                .map(HotelCheckInEntity::getTouristId)
                .toList();
    }













    public Page<HotelCheckInDto> updateHotelCheckIn(Integer hotelCheckinId, HotelCheckInDto dto, int pageSize, int pageNo, String sortBy, String sortDir){
        HotelCheckInEntity existing = hotelCheckInRepository.findById(hotelCheckinId).orElseThrow(() -> new RuntimeException("Hotel CheckIn not found with Id : " + hotelCheckinId));

        existing.setCheckInDate(dto.getCheckInDate());

        if(dto.getHotelId() != null){
            HotelEntity hotel = hotelRepository.findById(dto.getHotelId()).orElseThrow(() -> new RuntimeException("Update cannot be done, hotel cannot be found with Id: " + dto.getHotelId()));

            existing.setHotelId(hotel);
        }

        hotelCheckInRepository.save(existing);
        return getAllHotelCheckIns(pageNo, pageSize, sortBy, sortDir);



    }

    public Page<HotelCheckInDto> partialUpdateHotelCheckIn(Integer hotelCheckInId, HotelCheckInDto dto, int pageSize, int pageNo, String sortBy, String sortDir){
        HotelCheckInEntity existing = hotelCheckInRepository.findById(hotelCheckInId).orElseThrow(() -> new RuntimeException("Update cannot be done, hotel cannot be found with Id: " + dto.getCheckinId()));

        if(dto.getCheckInDate() != null){
            existing.setCheckInDate(dto.getCheckInDate());
        }

        if(dto.getHotelId() != null){
            HotelEntity hotel = hotelRepository.findById(dto.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Cannot proceed with update, hotel cannot be found with Id:" + dto.getHotelId()));
            existing.setHotelId(hotel);
        }

        hotelCheckInRepository.save(existing);
        return getAllHotelCheckIns(pageNo, pageSize, sortBy, sortDir);


    }

    public Page<HotelCheckInDto> deleteCheckIn(Integer hotelCheckInId, int pageNo, int pageSize, String sortBy, String sortDir){
        if(!hotelCheckInRepository.existsById(hotelCheckInId)){
            throw new RuntimeException("Deletion cannot be done, hotel CheckIn cannot be found with Id : " + hotelCheckInId);
        }
        hotelCheckInRepository.deleteById(hotelCheckInId);
        return getAllHotelCheckIns(pageNo, pageSize, sortBy, sortDir);
    }











}
