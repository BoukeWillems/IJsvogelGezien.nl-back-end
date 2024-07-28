package com.bouke.IJsvogelgezien.repository;

import com.bouke.IJsvogelgezien.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
    @Query("SELECT o FROM Observation o JOIN o.user u WHERE u.username = :username")
    List<Observation> findByUsername(@Param("username") String username);

    @Query("SELECT o FROM Observation o WHERE o.date BETWEEN :startDate AND :endDate")
    List<Observation> findByDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT o FROM Observation o WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(o.latitude)) * cos(radians(o.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(o.latitude)))) < :radius")
    List<Observation> findByLocationWithinRadius(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radius") double radius);

    @Query("SELECT o FROM Observation o WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(o.latitude)) * cos(radians(o.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(o.latitude)))) < :radius " +
            "ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(o.latitude)) * cos(radians(o.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(o.latitude)))) ASC")
    List<Observation> findTop5ByLocationWithinRadius(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radius") double radius);

    List<Observation> findTop5ByOrderByDateDesc();
}