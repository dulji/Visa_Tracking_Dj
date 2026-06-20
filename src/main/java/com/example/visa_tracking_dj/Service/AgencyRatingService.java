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

import java.util.NoSuchElementException;

@Service
public class AgencyRatingService {

    private final AgencyRatingRepository agencyRatingRepository;
    private final AgencyRepository agencyRepository;

    @Autowired
    public AgencyRatingService(AgencyRatingRepository agencyRatingRepository, AgencyRepository agencyRepository) {
        this.agencyRatingRepository = agencyRatingRepository;
        this.agencyRepository = agencyRepository;
    }

    private AgencyRatingDto convertToDto(AgencyRatingEntity entity) {
        return new AgencyRatingDto(
                entity.getRatingId(),
                entity.getAgencyId() != null ? entity.getAgencyId().getAgencyId() : null,
                entity.getScore(),
                entity.getComments()
        );
    }

    private AgencyRatingEntity convertToEntity(AgencyRatingDto dto) {
        AgencyRatingEntity entity = new AgencyRatingEntity();
        entity.setRatingId(dto.getRatingId());
        
        if (dto.getAgencyId() != null) {
            AgencyEntity agency = agencyRepository.findById(dto.getAgencyId())
                    .orElseThrow(() -> new NoSuchElementException("Agency not found with ID: " + dto.getAgencyId()));
            entity.setAgencyId(agency);
        }
        
        entity.setScore(dto.getScore());
        entity.setComments(dto.getComments());
        return entity;
    }

    public Page<AgencyRatingDto> getAllRatings(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<AgencyRatingEntity> ratingsPage = agencyRatingRepository.findAll(pageable);
        return ratingsPage.map(this::convertToDto);
    }

    public AgencyRatingDto getRatingById(Integer ratingId) {
        AgencyRatingEntity entity = agencyRatingRepository.findById(ratingId)
                .orElseThrow(() -> new NoSuchElementException("Rating not found with ID: " + ratingId));
        return convertToDto(entity);
    }

    public AgencyRatingDto createRating(AgencyRatingDto ratingDto) {
        AgencyRatingEntity entityToSave = convertToEntity(ratingDto);
        AgencyRatingEntity savedEntity = agencyRatingRepository.save(entityToSave);
        return convertToDto(savedEntity);
    }

    public AgencyRatingDto updateRating(Integer ratingId, AgencyRatingDto ratingDto) {
        AgencyRatingEntity existingEntity = agencyRatingRepository.findById(ratingId)
                .orElseThrow(() -> new NoSuchElementException("Rating not found with ID: " + ratingId));

        if (ratingDto.getAgencyId() != null) {
            AgencyEntity agency = agencyRepository.findById(ratingDto.getAgencyId())
                    .orElseThrow(() -> new NoSuchElementException("Agency not found with ID: " + ratingDto.getAgencyId()));
            existingEntity.setAgencyId(agency);
        } else {
            existingEntity.setAgencyId(null);
        }

        existingEntity.setScore(ratingDto.getScore());
        existingEntity.setComments(ratingDto.getComments());
        
        AgencyRatingEntity updatedEntity = agencyRatingRepository.save(existingEntity);
        return convertToDto(updatedEntity);
    }

    public void deleteRating(Integer ratingId) {
        if (!agencyRatingRepository.existsById(ratingId)) {
            throw new NoSuchElementException("Rating not found with ID: " + ratingId);
        }
        agencyRatingRepository.deleteById(ratingId);
    }
}
