package com.sankalp.AeroHorizon.demo;

import com.sankalp.AeroHorizon.dto.*;
import com.sankalp.AeroHorizon.entity.Booking;
import com.sankalp.AeroHorizon.entity.User;
import com.sankalp.AeroHorizon.service.AirPlaneService;
import com.sankalp.AeroHorizon.service.AuthenticationService;
import com.sankalp.AeroHorizon.service.BookingService;
import com.sankalp.AeroHorizon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/demo-controller")
@RequiredArgsConstructor
public class DemoController {

    private final AirPlaneService airPlaneService;
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final BookingService bookingService;

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "User/index";
    }

    @PostMapping("/dashboard")
    public String redirectToDashboard(@RequestBody AuthenticationRequest request) {
        return userService.checkUserRoleIsAdmin(request) ? "Admin/dashboard" : "User/index";
    }

    @PostMapping("/admin/plane")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addPlane(@RequestBody AirPlaneDto planeDto) {
        return airPlaneService.createNewPlane(planeDto);
    }

    @GetMapping("/admin/plane/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AirPlaneDto> getPlaneById(@PathVariable int id, Model model) {
        AirPlaneDto plane = airPlaneService.getPlaneById(id);
        model.addAttribute("Plane", plane);
        return ResponseEntity.ok(plane);
    }

    @GetMapping("/admin/planes")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<AirPlaneDto>> getAllPlanes(Model model) {
        List<AirPlaneDto> planes = airPlaneService.getAllPlanes();
        model.addAttribute("Planes", planes);
        return ResponseEntity.ok(planes);
    }

    @PutMapping("/admin/plane")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updatePlane(@RequestBody AirPlaneDto planeDto) {
        return airPlaneService.updatePlane(planeDto);
    }

    @DeleteMapping("/admin/plane/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deletePlane(@PathVariable int id) {
        return airPlaneService.deletePlaneById(id);
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("Users", users);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/admin/register")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.registerAdmin(request));
    }

    @GetMapping("/admin/bookings")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Booking>> getAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("Bookings", bookings);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/user/check-price")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Double> checkPrice(@RequestBody BookingDto bookingDto) {
        return bookingService.checkPrice(bookingDto);
    }

    @PostMapping("/user/booking")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> addBooking(@RequestBody BookingDto bookingDto) {
        return bookingService.addBooking(bookingDto);
    }
}
