package com.example.visa_tracking_dj.Service;

import com.example.visa_tracking_dj.Dto.AgencyRatingDto;
import com.example.visa_tracking_dj.Entity.AgencyEntity;
import com.example.visa_tracking_dj.Entity.AgencyRatingEntity;
import com.example.visa_tracking_dj.Repository.AgencyRatingRepository;
import com.example.visa_tracking_dj.Repository.AgencyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AgencyRatingService {

    private final AgencyRatingRepository agencyRatingRepository;
    private final AgencyRepository agencyRepository;

    public AgencyRatingService (AgencyRatingRepository agencyRatingRepository, AgencyRepository agencyRepository){
        this.agencyRatingRepository = agencyRatingRepository;
        this.agencyRepository = agencyRepository;
    }


    private AgencyRatingDto convertToDto(AgencyRatingEntity entity){
        return new AgencyRatingDto(
                entity.getRatingId(),
                entity.getAgencyId() != null? entity.getAgencyId().getAgencyId() : null,
                entity.getScore(),
                entity.getComments()
        );
    }

    private AgencyRatingEntity convertToEntity(AgencyRatingDto dto){
        AgencyRatingEntity entity = new AgencyRatingEntity();
        entity.setRatingId(dto.getRatingId());
        entity.setScore(dto.getScore());
        entity.setComments(dto.getComments());

        if (dto.getAgencyId() != null){
            AgencyEntity agency = agencyRepository.findById(dto.getAgencyId()).orElseThrow(() -> new RuntimeException("Agency not found with ID: " + dto.getAgencyId() ));
            entity.setAgencyId(agency);
        }
        return entity;

    }

    public Page<AgencyRatingDto> getAllRatings(int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<AgencyRatingEntity> entitesPage = agencyRatingRepository.findAll(pageable);

        return entitesPage.map(this::convertToDto);

    }

    public AgencyRatingDto getRatingById(Integer ratingId){
        AgencyRatingEntity entity = agencyRatingRepository.findById(ratingId).orElseThrow(() -> new RuntimeException("Agency Rating not found with ID: " + ratingId));
        return convertToDto(entity);

    }

    public Page<AgencyRatingDto> createRating(AgencyRatingDto dto, int pageNo, int pageSize, String sortBy, String sortDir){
        AgencyRatingEntity entity = convertToEntity(dto);
        agencyRatingRepository.save(entity);
        return getAllRatings(pageNo, pageSize, sortBy, sortDir);
    }

    public Page<AgencyRatingDto> updateRating(Integer ratingId, AgencyRatingDto dto, int pageNo, int pageSize, String sortBy, String sortDir){
        AgencyRatingEntity existingEntity = agencyRatingRepository.findById(ratingId)
                .orElseThrow(()-> new RuntimeException("Cannot update. Rating record not found with ID: " + dto.getAgencyId()));

        existingEntity.setScore(dto.getScore());
        existingEntity.setComments(dto.getComments());

        return getAllRatings(pageNo, pageSize, sortBy, sortDir);

    }

    public Page<AgencyRatingDto> deleteRating(Integer ratingId, int pageNo, int pageSize, String sortBy, String sortDir){
        if(!agencyRatingRepository.existsById(ratingId)){
            throw new RuntimeException("Cannot delete record (Not found) with ID : " + ratingId);
        }
        agencyRatingRepository.deleteById(ratingId);

        return getAllRatings(pageNo, pageSize, sortBy, sortDir);
    }

}
