package com.ticbus.backend.repository;

import com.ticbus.backend.model.BusSeatDiagram;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BusSeatDiagramRepository extends JpaRepository<BusSeatDiagram, Integer>,
    JpaSpecificationExecutor<BusSeatDiagram> {

  Optional<BusSeatDiagram> findById(Integer id);

  Optional<BusSeatDiagram> findByName(String name);

  Page<BusSeatDiagram> findAll(Specification<BusSeatDiagram> spec, Pageable pageable);
}

