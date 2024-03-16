package com.ticbus.backend.repository;

import com.ticbus.backend.model.City;
import com.ticbus.backend.model.Scheduler;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface SchedulerRepository extends JpaRepository
    <Scheduler, Integer>, JpaSpecificationExecutor<Scheduler> {

    Page<Scheduler> findAll(Specification<Scheduler> spec, Pageable pageable);

    Boolean existsByBusIdAndAndStartTime(Integer busId, Date startTime);

    Boolean existsByBusIdAndTripIdAndStartTime(Integer busId, Integer tripId, Date startTime);

    Optional<Scheduler> findSchedulerById(Integer id);

    @Query(value = "Select * from tb_scheduler u where u.bus_id=?1 "
        + "and u.start_time <= ?2 "
        + "and u.endTime >= ?3", nativeQuery = true)
    List<Scheduler> findByBusAndRangeTime(Integer busId, Date endTime, Date startTime);
}
