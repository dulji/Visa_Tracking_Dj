package com.example.visa_tracking_dj.Service;

import com.example.visa_tracking_dj.Dto.AgencyRatingDto;
import com.example.visa_tracking_dj.Entity.AgencyEntity;
import com.example.visa_tracking_dj.Entity.AgencyRatingEntity;
import com.example.visa_tracking_dj.Repository.AgencyRatingRepository;
import com.example.visa_tracking_dj.Repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AgencyRatingService {

    @Autowired
    private AgencyRatingRepository agencyRatingRepository;

    @Autowired
    private AgencyRepository agencyRepository;

//    public AgencyRatingService (AgencyRatingRepository agencyRatingRepository, AgencyRepository agencyRepository){
//        this.agencyRatingRepository = agencyRatingRepository;
//        this.agencyRepository = agencyRepository;
//    }


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

    public AgencyRatingDto createRating(AgencyRatingDto dto){
        AgencyRatingEntity entity = convertToEntity(dto);
//        entity.setAgencyId(null);
        AgencyRatingEntity savedEntity = agencyRatingRepository.save(entity);
        return convertToDto(savedEntity);
    }

    public AgencyRatingDto updateRating(Integer ratingId, AgencyRatingDto dto){
        AgencyRatingEntity existingEntity = agencyRatingRepository.findById(ratingId)
                .orElseThrow(()-> new RuntimeException("Cannot update. Rating record not found with ID: " + dto.getAgencyId()));

        existingEntity.setScore(dto.getScore());
        existingEntity.setComments(dto.getComments());

        AgencyRatingEntity savedEntity = agencyRatingRepository.save(existingEntity);
        return convertToDto(savedEntity);

    }

    public AgencyRatingDto partialUpdateAgencyRating(Integer ratingId, AgencyRatingDto dto){
        AgencyRatingEntity existingEntity = agencyRatingRepository.findById(ratingId).orElseThrow(() -> new RuntimeException("Cannot update, rating not found with Id : " + ratingId));

        if(dto.getComments() != null){
            existingEntity.setComments(dto.getComments());
        }
        if(dto.getScore() != null){
            existingEntity.setScore(dto.getScore());
        }
        if(dto.getAgencyId() != null){
            AgencyEntity parentId = agencyRepository.findById(dto.getAgencyId()).orElseThrow(() -> new RuntimeException("Parent agency not found with Id : " + dto.getAgencyId()));
            existingEntity.setAgencyId(parentId);
        }

        AgencyRatingEntity savedEntity = agencyRatingRepository.save(existingEntity);
        return convertToDto(savedEntity);
    }

    public void deleteRating(Integer ratingId){
        if(!agencyRatingRepository.existsById(ratingId)){
            throw new RuntimeException("Cannot delete record (Not found) with ID : " + ratingId);
        }
        agencyRatingRepository.deleteById(ratingId);

    }

}
