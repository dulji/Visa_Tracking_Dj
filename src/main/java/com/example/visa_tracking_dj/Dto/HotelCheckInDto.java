package com.example.visa_tracking_dj.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelCheckInDto {
    private Integer checkinId;
    private Integer hotelId;
    private Long touristId;
    private Date checkInDate;

}
