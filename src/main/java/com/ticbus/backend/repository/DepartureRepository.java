package com.ticbus.backend.repository;

import com.ticbus.backend.model.Departure;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartureRepository extends PagingAndSortingRepository<Departure, Integer> {

  Optional<Departure> findByName(String name);

  boolean existsByName(String name);

  Optional<Departure> findByAddress(String address);

  boolean existsByAddress(String address);

  Page<Departure> findByName(String name, Pageable pageable);

  @Query(value = "select * from tb_departure where name like %:name%", nativeQuery = true)
  List<Departure> searchByName(@Param("name") String name);

  @Query(value = "select * from tb_departure where address like %:address%", nativeQuery = true)
  List<Departure> searchByAddress(@Param("address") String address);

  Page<Departure> findAll(Pageable pageable);

  List<Departure> findByCity_Id(Integer id);
}
