package com.example.visa_tracking_dj.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AgencyRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ratingId;

    //agency id
    @ManyToOne
    @JoinColumn(name="agency_id", nullable=false)
    private Agency agencyId;

    private Integer score;
    private String comments;
}
