package com.ticbus.backend.repository;

import com.ticbus.backend.model.Customer;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author AnhLH
 */
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>,
    JpaSpecificationExecutor<Customer> {

  Optional<Customer> findByPhoneAndIsActive(String phone, boolean isActive);

  Optional<Customer> findByPhone(String phone);

  Optional<Customer> findByPhoneAndStatus(String phone, int status);

  boolean existsByPhone(String phone);

  Page<Customer> findAll(Pageable pageable);
}
