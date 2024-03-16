package com.ticbus.backend.services;

import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.exception.EntityType;
import com.ticbus.backend.exception.ExceptionType;
import com.ticbus.backend.exception.TicbusException;
import com.ticbus.backend.model.Customer;
import com.ticbus.backend.model.specification.UserSpecification;
import com.ticbus.backend.payload.request.CustomerListRequest;
import com.ticbus.backend.payload.request.CustomerUpdateRequest;
import com.ticbus.backend.payload.request.PageRequest;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.repository.CustomerRepository;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private UserSpecification userSpecification;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public GetArrayResponse<?> getAll(PageRequest request) {
    GetArrayResponse<Customer> res = new GetArrayResponse<>();
    try {
      String[] sortSplit = request.getSort().split(",");
      Page<Customer> customerList = customerRepository.findAll(
          new org.springframework.data.domain.PageRequest(request.getPage(), request.getSize(),
              (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC),
              sortSplit[0]));
      res.setSuccess(customerList.getContent(), customerList.getTotalElements());
    } catch (Exception e) {
      res.setFailed(e.getMessage());
    }
    return res;
  }

  @Cacheable(value = "customer", key = "#id")
  public Customer findUserById(Integer id) {
    return customerRepository.findById(id).orElseThrow(() -> exception(EntityType.CUSTOMER,
        ExceptionType.ENTITY_NOT_FOUND, id.toString()));
  }

//  @CachePut(value = "customer", key = "#id")
  public Customer changeUserStatus(CustomerUpdateRequest request, int id) {
    return customerRepository.findById(id).map(customer -> {
      if (request.getStatus() != null) {
        customer.setStatus(request.getStatus().getId());
      }
      customer.setLastModifiedDate(Date.from(Instant.now()));
      return customerRepository.save(customer);
    }).orElseThrow(
        () -> exception(EntityType.CUSTOMER, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(id)));
  }

  public Page<Customer> getAllCustomers(CustomerListRequest request) {
    String[] sortSplit = request.getSort().split(",");
    if (!Objects.isNull(request.getSearch())) {
      return customerRepository.findAll(UserSpecification.textInAllColumns(request.getSearch()),
          new org.springframework.data.domain.PageRequest(request.getPage(), request.getSize(),
              (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC :
                  Sort.Direction.DESC), sortSplit[0]));
    } else {
      return customerRepository.findAll(userSpecification.getFilter(request),
          new org.springframework.data.domain.PageRequest(request.getPage(), request.getSize(),
              (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC :
                  Sort.Direction.DESC), sortSplit[0]));
    }
  }

  /**
   * Returns a new RuntimeException
   */
  private RuntimeException exception(EntityType entityType, ExceptionType exceptionType,
      String... args) {
    return TicbusException.throwException(entityType, exceptionType, args);
  }

  /**
   * Returns a new RuntimeException
   */
  private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType,
      String id, String... args) {
    return TicbusException.throwExceptionWithId(entityType, exceptionType, id, args);
  }
}
