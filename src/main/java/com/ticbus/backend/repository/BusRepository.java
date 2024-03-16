package com.ticbus.backend.repository;

import com.ticbus.backend.model.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends PagingAndSortingRepository<Bus,Integer>,
        JpaSpecificationExecutor<Bus> {

    Optional<Bus> findBusByLicensePlates(String licensePlates);

    Optional<Bus> findBusById(Integer id);
    boolean existsById(Integer id);

    Page<Bus> findAll(Specification<Bus> spec, Pageable pageable);
}
