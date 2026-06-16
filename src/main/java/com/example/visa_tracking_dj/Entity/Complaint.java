package com.example.visa_tracking_dj.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer complaintId;

    //agency Id
    @ManyToOne
    @JoinColumn(name="agency_Id", nullable = false)
    private Agency agentId;

    private String description;
    private Boolean status;
}
