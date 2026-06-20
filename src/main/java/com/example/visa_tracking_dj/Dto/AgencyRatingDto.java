package com.example.visa_tracking_dj.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyRatingDto {
    private Integer ratingId;
    private Integer agencyId;
    private Integer score;
    private String comments;
}
