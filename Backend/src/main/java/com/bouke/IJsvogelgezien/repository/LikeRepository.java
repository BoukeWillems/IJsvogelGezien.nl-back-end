package com.bouke.IJsvogelgezien.repository;

import com.bouke.IJsvogelgezien.model.Like;
import com.bouke.IJsvogelgezien.model.Observation;
import com.bouke.IJsvogelgezien.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {


    List<Like> findByObservationId(Long ObservationId);

    Optional<Like> findByUserAndObservation(User user, Observation observation);
}