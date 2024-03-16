package com.ticbus.backend.repository;

import com.ticbus.backend.model.Employee;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author AnhLH
 */
@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer>,
    JpaSpecificationExecutor<Employee> {

  Optional<Employee> findByPhone(String phone);

  Optional<Employee> findEmployeeById(Integer id);

  Boolean existsByPhone(String phone);

  Page<Employee> findAll(Specification<Employee> spec, Pageable pageable);
}
