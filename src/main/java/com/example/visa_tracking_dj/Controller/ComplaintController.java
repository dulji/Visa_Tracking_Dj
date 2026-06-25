package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.ComplaintDto;
import com.example.visa_tracking_dj.Repository.ComplaintRepository;
import com.example.visa_tracking_dj.Service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaint")
public class ComplaintController {

    private ComplaintService complaintService;

    @GetMapping
    public ResponseEntity<Page<ComplaintDto>> getAllComplaints(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "complaintId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return ResponseEntity.ok(complaintService.getAllComplaints(pageNo, pageSize, sortBy, sortDir));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDto> getComplaintById(@PathVariable Integer complainId){
        return ResponseEntity.ok(complaintService.getComplaintById(complainId));
    }

    @PostMapping
    public ResponseEntity<Page<ComplaintDto>> createComplaint(
            @RequestBody ComplaintDto dto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "complaintId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        Page<ComplaintDto> updatedPage = complaintService.createComplaint(dto, pageNo, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(updatedPage, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Page<ComplaintDto>> updateComplaint(
            @PathVariable Integer complaintId,
            @RequestBody ComplaintDto dto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "complaintId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            Sort sort){
        Page<ComplaintDto> updatedPage = complaintService.updateComplaint(complaintId, dto, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Page<ComplaintDto>> patchComplaint(
            @PathVariable Integer complaintId,
            @RequestBody ComplaintDto dto,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "complaintId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){
        Page<ComplaintDto> updatedPage = complaintService.partialUpdateComplaint(complaintId, dto, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Page<ComplaintDto>> deleteComplaint(
            @PathVariable Integer complaintId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "complaintId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        Page<ComplaintDto> updatedPage = complaintService.deleteComplaint(complaintId, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(updatedPage);
    }


}