package com.example.visa_tracking_dj.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HotelCheckInEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer checkinId;

    //hotel id
    @ManyToOne
    @JoinColumn(name="hotel_Id", nullable = false)
    private HotelEntity hotelId;

    private Date checkInDate;

    //tourist id
//    @ManyToOne
//    @JoinColumn(name="tourist_Id", nullable = false)
//    private Tourist touristId;




}
