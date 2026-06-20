package com.example.visa_tracking_dj.Controller;

import com.example.visa_tracking_dj.Dto.ComplaintDto;
import com.example.visa_tracking_dj.Service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<ComplaintDto> createComplaint(@RequestBody ComplaintDto complaintDto) {
        ComplaintDto createdComplaint = complaintService.createComplaint(complaintDto);
        return new ResponseEntity<>(createdComplaint, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ComplaintDto>> getAllComplaints(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "complaintId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        Page<ComplaintDto> complaints = complaintService.getAllComplaints(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDto> getComplaintById(@PathVariable("id") Integer id) {
        ComplaintDto complaint = complaintService.getComplaintById(id);
        return ResponseEntity.ok(complaint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplaintDto> updateComplaint(
            @PathVariable("id") Integer id,
            @RequestBody ComplaintDto complaintDto
    ) {
        ComplaintDto updatedComplaint = complaintService.updateComplaint(id, complaintDto);
        return ResponseEntity.ok(updatedComplaint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable("id") Integer id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.noContent().build();
    }
}