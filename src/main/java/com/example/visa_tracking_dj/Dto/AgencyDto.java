package com.example.visa_tracking_dj.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyDto {
        private Integer agencyId;
        private String agencyName;
        private Integer licenseNumber;
        private Boolean status;
        private String email;

}
