package com.sankalp.AeroHorizon.service;

import com.sankalp.AeroHorizon.dto.BookingDto;
import com.sankalp.AeroHorizon.entity.AirPlane;
import com.sankalp.AeroHorizon.entity.Booking;
import com.sankalp.AeroHorizon.repository.BookingRepository;
import com.sankalp.AeroHorizon.repository.PlaneRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final PlaneRepository planeRepository;
    private final BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public ResponseEntity<Double> checkPrice(BookingDto bookingDto) {
        AirPlane availablePlane = findAvailablePlane(bookingDto);
        if (availablePlane != null) {
            Double price = calculateFlightFare(availablePlane.getDistanceInKm(), bookingDto.getNumberOfSeats());
            return ResponseEntity.ok(price);
        }
        return ResponseEntity.status(401)
                             .body(null);
    }

    private AirPlane findAvailablePlane(BookingDto bookingDto) {
        return planeRepository.findByStartAndEnd(bookingDto.getStartLocation(), bookingDto.getEndLocation());
    }

    private Double calculateFlightFare(Double distanceInKm, Integer numberOfSeats) {
        final double PRICE_PER_KM = 10000.00;
        return distanceInKm * numberOfSeats * PRICE_PER_KM;
    }

    public ResponseEntity<String> addBooking(BookingDto bookingDto) {
        AirPlane availablePlane = findAvailablePlane(bookingDto);
        if (availablePlane == null) {
            return ResponseEntity.status(401)
                                 .body("The plane is not found");
        }

        if (availablePlane.getAvailableSeats() < bookingDto.getNumberOfSeats()) {
            return ResponseEntity.status(401)
                                 .body("Seats are not available");
        }

        Double bookingPrice = calculateFlightFare(availablePlane.getDistanceInKm(), bookingDto.getNumberOfSeats());
        Booking booking = Booking.builder()
                                 .price(bookingPrice)
                                 .startLocation(bookingDto.getStartLocation())
                                 .endLocation(bookingDto.getEndLocation())
                                 .numberOfSeats(bookingDto.getNumberOfSeats())
                                 .build();

        bookingRepository.save(booking);
        availablePlane.setAvailableSeats(availablePlane.getAvailableSeats() - bookingDto.getNumberOfSeats());
        planeRepository.save(availablePlane);

        return ResponseEntity.ok("Your booking has been recorded");
    }


}