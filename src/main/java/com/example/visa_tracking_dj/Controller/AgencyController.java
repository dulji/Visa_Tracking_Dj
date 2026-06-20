package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.AgencyDto;
import com.example.visa_tracking_dj.Service.AgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agencies")
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;

    @PostMapping
    public ResponseEntity<AgencyDto> createAgency(@RequestBody AgencyDto agencyDto) {
        AgencyDto createdAgency = agencyService.createAgency(agencyDto);
        return new ResponseEntity<>(createdAgency, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AgencyDto>> getAllAgencies(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "agencyName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        Page<AgencyDto> agencies = agencyService.getAllAgencies(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(agencies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyDto> getAgencyById(@PathVariable Integer id) {
        AgencyDto agency = agencyService.getAgencyById(id);
        return ResponseEntity.ok(agency);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgencyDto> updateAgency(
            @PathVariable Integer id,
            @RequestBody AgencyDto agencyDto
    ) {
        AgencyDto updatedAgency = agencyService.updateAgency(id, agencyDto);
        return ResponseEntity.ok(updatedAgency);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Integer id) {
        agencyService.deleteAgency(id);
        return ResponseEntity.noContent().build();
    }
}
