package com.sankalp.AeroHorizon.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private String startLocation;
    private String endLocation;
    private Integer numberOfSeats;
    private Double price;

}
