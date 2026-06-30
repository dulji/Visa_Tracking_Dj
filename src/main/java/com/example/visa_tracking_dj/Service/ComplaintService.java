package com.example.visa_tracking_dj.Service;

import ch.qos.logback.classic.spi.IThrowableProxy;
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
public class ComplaintService  {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    public ComplaintService(ComplaintRepository complaintRepository, AgencyRepository agencyRepository){
        this.complaintRepository = complaintRepository;
        this.agencyRepository = agencyRepository;
    }

    public ComplaintDto convertToDto(ComplaintEntity entity){
        return new ComplaintDto(
                entity.getComplaintId(),
                entity.getAgencyId() != null ? entity.getAgencyId().getAgencyId() : null,
                entity.getDescription(),
                entity.getStatus()
        );
    }

    public ComplaintEntity convertToEntity(ComplaintDto dto){
        ComplaintEntity entity = new ComplaintEntity();

        entity.setComplaintId(dto.getComplaintId());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());

        if (dto.getAgencyId() != null){
            AgencyEntity agency = agencyRepository.findById(dto.getAgencyId()).orElseThrow(() -> new RuntimeException("Agency not found with ID: " + dto.getAgencyId() ));
            entity.setAgencyId(agency);

        }
        return entity;


    }

    public Page<ComplaintDto> getAllComplaints(int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ComplaintEntity> entitesPage = complaintRepository.findAll(pageable);

        return entitesPage.map(this::convertToDto);
    }

    public ComplaintDto getComplaintById(Integer complaintId){
        ComplaintEntity entity = complaintRepository.findById(complaintId).orElseThrow(() -> new RuntimeException("Complaint not found with Id : " + complaintId));

        return convertToDto(entity);
    }

    public ComplaintDto createComplaint(ComplaintDto dto){
        ComplaintEntity entity = convertToEntity(dto);
        entity.setComplaintId(null);

        ComplaintEntity createdEntity = complaintRepository.save(entity);
        return convertToDto(createdEntity);

    }

    public ComplaintDto updateComplaint(Integer complaintId, ComplaintDto dto){
        ComplaintEntity existingEntity = complaintRepository.findById(complaintId)
                .orElseThrow(()-> new RuntimeException("Cannot find complaint with Id : " + complaintId));
        existingEntity.setDescription(dto.getDescription());
        existingEntity.setStatus(dto.getStatus());

        if (dto.getAgencyId() != null){
            AgencyEntity agency = agencyRepository.findById(dto.getAgencyId()).orElseThrow(() -> new RuntimeException("Agency not found with ID: " + dto.getAgencyId() ));
            existingEntity.setAgencyId(agency);
        }

        ComplaintEntity savedEntity = complaintRepository.save(existingEntity);

        return convertToDto(savedEntity);

    }

    public ComplaintDto partialUpdateComplaint(Integer complaintId, ComplaintDto dto){
        ComplaintEntity existingEntity = complaintRepository.findById(complaintId).orElseThrow(() -> new NoSuchElementException("Cannot partially update. Complaint record not found with ID: " + complaintId));

        if(dto.getDescription() != null){
            existingEntity.setDescription(dto.getDescription());
        }
        if(dto.getStatus() != null){
            existingEntity.setStatus(dto.getStatus());
        }
        if(dto.getAgencyId() != null){
            AgencyEntity agency = agencyRepository.findById(dto.getAgencyId()).orElseThrow(() -> new RuntimeException("Agency not found with Id : " + dto.getAgencyId()));
            existingEntity.setAgencyId(agency);
        }

        ComplaintEntity savedEntity = complaintRepository.save(existingEntity);

        return convertToDto(savedEntity);

    }

    public void deleteComplaint(Integer complaintId){
        if(!complaintRepository.existsById(complaintId)){
            throw new RuntimeException("Cannot delete since complaint record not found with Id : " + complaintId);

        }

        complaintRepository.deleteById(complaintId);
    }


}
