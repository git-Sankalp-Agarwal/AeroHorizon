package com.sankalp.AeroHorizon.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirPlaneDto {
    private Long id;
    private String start;
    private String end;
    private Integer availableSeats;
    private Double distanceInKm;
    private Double price;

}
