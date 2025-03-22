package com.sankalp.AeroHorizon.repository;


import com.sankalp.AeroHorizon.entity.AirPlane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlaneRepository extends JpaRepository<AirPlane,Long> {

    @Query("SELECT a FROM AirPlane a WHERE a.start = ?1 AND a.end = ?2")
    AirPlane findByStartAndEnd(String start,String end);

    Optional<AirPlane> findById(int id);
}
