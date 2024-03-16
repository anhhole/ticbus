package com.ticbus.backend.repository;

import com.ticbus.backend.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CityRepository extends PagingAndSortingRepository<City,Integer>,
        JpaSpecificationExecutor<City> {

  Page<City> findAll(Specification<City> spec, Pageable pageable);

  Optional<City> findCityById(Integer id);

  boolean existsById(Integer id);
}
