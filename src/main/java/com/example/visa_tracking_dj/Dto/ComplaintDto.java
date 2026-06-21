package com.example.visa_tracking_dj.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDto {
    private Integer complaintId;
    private Integer agencyId;
    private String description;
    private Boolean status;
}
