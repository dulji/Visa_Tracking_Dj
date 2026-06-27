package com.example.visa_tracking_dj.Dto;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Date;

public class HotelBookingDto {
    private Integer bookingId;
    private Integer hotelId;
    private Long touristId;
    private Date checkInDate;

}
