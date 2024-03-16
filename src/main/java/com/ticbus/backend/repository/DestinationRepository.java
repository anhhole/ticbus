package com.ticbus.backend.repository;

import com.ticbus.backend.model.Destination;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Integer> {

  Optional<Destination> findByName(String name);

  boolean existsByName(String name);

  Optional<Destination> findByAddress(String address);

  boolean existsByAddress(String address);

  @Query(value = "select * from tb_destination where address like %:address%", nativeQuery = true)
  List<Destination> searchByAddress(@Param("address") String address);

  Page<Destination> findAll(Pageable pageable);

  List<Destination> findByCity_Id(Integer id);
}
