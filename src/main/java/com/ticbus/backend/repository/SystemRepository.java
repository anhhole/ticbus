package com.ticbus.backend.repository;

import com.ticbus.backend.model.SystemInformation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRepository extends JpaRepository<SystemInformation, Integer> {

  Optional<SystemInformation> findSystemInformationById(String id);
}
