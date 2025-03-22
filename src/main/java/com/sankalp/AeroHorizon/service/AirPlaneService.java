package com.sankalp.AeroHorizon.service;

import com.sankalp.AeroHorizon.dto.AirPlaneDto;
import com.sankalp.AeroHorizon.entity.AirPlane;
import com.sankalp.AeroHorizon.repository.PlaneRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirPlaneService {

    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<String> createNewPlane(AirPlaneDto airPlaneDto) {
        if (planeRepository.findByStartAndEnd(airPlaneDto.getStart(), airPlaneDto.getEnd())!=null) {
            return ResponseEntity.status(400).body("The plane already exists");
        }
        AirPlane airPlane = modelMapper.map(airPlaneDto, AirPlane.class);
        planeRepository.save(airPlane);
        return ResponseEntity.ok("Successfully added");
    }

    public ResponseEntity<String> updatePlane(AirPlaneDto airPlaneDto) {
        AirPlane existingPlane = planeRepository.findById(airPlaneDto.getId()).orElse(null);
        if (existingPlane == null) {
            return ResponseEntity.status(404).body("The plane does not exist");
        }
        modelMapper.map(airPlaneDto, existingPlane);
        planeRepository.save(existingPlane);
        return ResponseEntity.ok("Successfully updated");
    }

    public AirPlaneDto getPlaneById(int id) {
        AirPlane existingPlane = planeRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Plane not found"));
        return modelMapper.map(existingPlane, AirPlaneDto.class);
    }

    public List<AirPlaneDto> getAllPlanes() {
        List<AirPlane> airPlanes = planeRepository.findAll();
        return modelMapper.map(airPlanes, new TypeToken<List<AirPlaneDto>>() {}.getType());
    }

    public ResponseEntity<String> deletePlaneById(int id) {
        AirPlane existingPlane = planeRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Plane not found"));
        planeRepository.delete(existingPlane);
        return ResponseEntity.ok("The airplane was successfully deleted");
    }
}
