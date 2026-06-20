package com.example.visa_tracking_dj.Service;

import com.example.visa_tracking_dj.Entity.AgencyEntity;
import com.example.visa_tracking_dj.Repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.NoSuchElementException;

public class AgencyService{

    @Autowired
    private final AgencyRepository agencyRepository;

    public AgencyService (AgencyRepository agencyRepository){
        this.agencyRepository = agencyRepository;

    }


    public Page<AgencyEntity> getAllAgencyEntity(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return agencyRepository.findAll(pageable);
    }

    public AgencyEntity getAgencyById(Integer agencyId){
        return agencyRepository.findById(agencyId).orElseThrow(()-> new NoSuchElementException("Agency record not found with ID: " + agencyId));
    }

    public Page<AgencyEntity> createAgency(AgencyEntity newAgency, int pageNo, int pageSize, String sortBy, String sortDir){
        agencyRepository.save(newAgency);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return agencyRepository.findAll(pageable);
    }




}
