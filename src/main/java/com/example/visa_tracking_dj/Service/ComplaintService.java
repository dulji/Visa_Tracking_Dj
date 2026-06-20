package com.example.visa_tracking_dj.Service;

import com.example.visa_tracking_dj.Dto.ComplaintDto;
import com.example.visa_tracking_dj.Entity.AgencyEntity;
import com.example.visa_tracking_dj.Entity.ComplaintEntity;
import com.example.visa_tracking_dj.Repository.AgencyRepository;
import com.example.visa_tracking_dj.Repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final AgencyRepository agencyRepository;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository, AgencyRepository agencyRepository) {
        this.complaintRepository = complaintRepository;
        this.agencyRepository = agencyRepository;
    }

    private ComplaintDto convertToDto(ComplaintEntity entity) {
        return new ComplaintDto(
                entity.getComplaintId(),
                entity.getAgencyId() != null ? entity.getAgencyId().getAgencyId() : null,
                entity.getDescription(),
                entity.getStatus()
        );
    }

    private ComplaintEntity convertToEntity(ComplaintDto dto) {
        ComplaintEntity entity = new ComplaintEntity();
        entity.setComplaintId(dto.getComplaintId());

        if (dto.getAgencyId() != null) {
            AgencyEntity agency = agencyRepository.findById(dto.getAgencyId())
                    .orElseThrow(() -> new NoSuchElementException("Agency not found with ID: " + dto.getAgencyId()));
            entity.setAgencyId(agency);
        }

        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public Page<ComplaintDto> getAllComplaints(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ComplaintEntity> complaintsPage = complaintRepository.findAll(pageable);
        return complaintsPage.map(this::convertToDto);
    }

    public ComplaintDto getComplaintById(Integer complaintId) {
        ComplaintEntity entity = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new NoSuchElementException("Complaint not found with ID: " + complaintId));
        return convertToDto(entity);
    }

    public ComplaintDto createComplaint(ComplaintDto complaintDto) {
        ComplaintEntity entityToSave = convertToEntity(complaintDto);
        ComplaintEntity savedEntity = complaintRepository.save(entityToSave);
        return convertToDto(savedEntity);
    }

    public ComplaintDto updateComplaint(Integer complaintId, ComplaintDto complaintDto) {
        ComplaintEntity existingEntity = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new NoSuchElementException("Complaint not found with ID: " + complaintId));

        if (complaintDto.getAgencyId() != null) {
            AgencyEntity agency = agencyRepository.findById(complaintDto.getAgencyId())
                    .orElseThrow(() -> new NoSuchElementException("Agency not found with ID: " + complaintDto.getAgencyId()));
            existingEntity.setAgencyId(agency);
        } else {
            existingEntity.setAgencyId(null);
        }

        existingEntity.setDescription(complaintDto.getDescription());
        existingEntity.setStatus(complaintDto.getStatus());

        ComplaintEntity updatedEntity = complaintRepository.save(existingEntity);
        return convertToDto(updatedEntity);
    }

    public void deleteComplaint(Integer complaintId) {
        if (!complaintRepository.existsById(complaintId)) {
            throw new NoSuchElementException("Complaint not found with ID: " + complaintId);
        }
        complaintRepository.deleteById(complaintId);
    }
}
