package com.ticbus.backend.repository;

import com.ticbus.backend.model.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends PagingAndSortingRepository<Trip, Integer>, JpaSpecificationExecutor<Trip> {


    Page<Trip> findAll(Specification<Trip> spec, Pageable pageable);

    Optional<Trip> findTripById(Integer id);

    Optional<Trip> findByDepartureIdAndDestinationId(Integer departId, Integer desId);

    boolean existsByName(String tripName);


}
