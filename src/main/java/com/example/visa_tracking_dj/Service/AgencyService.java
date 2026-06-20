package com.example.visa_tracking_dj.Service;

import com.example.visa_tracking_dj.Dto.AgencyDto;
import com.example.visa_tracking_dj.Entity.AgencyEntity;
import com.example.visa_tracking_dj.Repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AgencyService {

    private final AgencyRepository agencyRepository;

    @Autowired
    public AgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    private AgencyDto convertToDto(AgencyEntity entity) {
        return new AgencyDto(
                entity.getAgencyId(),
                entity.getAgencyName(),
                entity.getLicenseNumber(),
                entity.getStatus()
        );
    }

    private AgencyEntity convertToEntity(AgencyDto dto) {
        AgencyEntity entity = new AgencyEntity();
        entity.setAgencyId(dto.getAgencyId());
        entity.setAgencyName(dto.getAgencyName());
        entity.setLicenseNumber(dto.getLicenseNumber());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public Page<AgencyDto> getAllAgencies(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<AgencyEntity> agencyEntitiesPage = agencyRepository.findAll(pageable);
        return agencyEntitiesPage.map(this::convertToDto);
    }

    public AgencyDto getAgencyById(Integer agencyId) {
        AgencyEntity entity = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new NoSuchElementException("Agency not found with ID: " + agencyId));
        return convertToDto(entity);
    }

    public AgencyDto createAgency(AgencyDto agencyDto) {
        AgencyEntity entityToSave = convertToEntity(agencyDto);
        AgencyEntity savedEntity = agencyRepository.save(entityToSave);
        return convertToDto(savedEntity);
    }

    public AgencyDto updateAgency(Integer agencyId, AgencyDto agencyDto) {
        AgencyEntity existingEntity = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new NoSuchElementException("Agency not found with ID: " + agencyId));
        existingEntity.setAgencyName(agencyDto.getAgencyName());
        existingEntity.setLicenseNumber(agencyDto.getLicenseNumber());
        existingEntity.setStatus(agencyDto.getStatus());
        AgencyEntity updatedEntity = agencyRepository.save(existingEntity);
        return convertToDto(updatedEntity);
    }

    public void deleteAgency(Integer agencyId) {
        if (!agencyRepository.existsById(agencyId)) {
            throw new NoSuchElementException("Agency not found with ID: " + agencyId);
        }
        agencyRepository.deleteById(agencyId);
    }
}
