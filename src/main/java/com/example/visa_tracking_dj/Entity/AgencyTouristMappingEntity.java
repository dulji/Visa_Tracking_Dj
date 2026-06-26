package com.example.visa_tracking_dj.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agency_tourist_mapping")
public class AgencyTouristMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mappingId;

    @ManyToOne
    @JoinColumn(name="agency_Id", nullable = false)
    private AgencyEntity agency;

    @Column(name="tourist_id", nullable = false)
    private Long touristId;

}
