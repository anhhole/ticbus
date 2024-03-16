package com.ticbus.backend.services;

import com.ticbus.backend.common.enumeration.EnumRole;
import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.exception.EntityType;
import com.ticbus.backend.exception.ExceptionType;
import com.ticbus.backend.exception.TicbusException;
import com.ticbus.backend.model.Employee;
import com.ticbus.backend.model.RefreshToken;
import com.ticbus.backend.model.specification.EmployeeSpecification;
import com.ticbus.backend.payload.request.EmployeeListRequest;
import com.ticbus.backend.payload.request.EmployeeRequest;
import com.ticbus.backend.payload.request.EmployeeUpdateRequest;
import com.ticbus.backend.repository.EmployeeRepository;
import com.ticbus.backend.util.PhoneUtils;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private RefreshTokenService refreshTokenService;
  @Autowired
  private EmployeeSpecification employeeSpecification;

  public Employee createNewEmployee(EmployeeRequest request) {
    if (StringUtils.isNotEmpty(request.getPhone()) && PhoneUtils.getInstance()
        .isValidPhone(request.getPhone())) {
      if (!checkEmployeeExistedByPhoneNumber(request.getPhone())) {
        Employee employee = new Employee();
        employee.setPhone(request.getPhone());
        employee.setName(request.getName());
        employee.setAddress(request.getAddress());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setCreatedTime(Date.from(Instant.now()));
        employee.setRole(EnumRole.USER.getId());
        employee.setStatus(EnumStatus.ENABLE.getId());
        employee.setMail(request.getEmail());
        return employeeRepository.save(employee);
      }
      throw exception(EntityType.EMPLOYEE, ExceptionType.DUPLICATE_ENTITY, request.getPhone());
    }
    throw exception(EntityType.EMPLOYEE, ExceptionType.ENTITY_EXCEPTION, request.getPhone());
  }

  private boolean checkEmployeeExistedByPhoneNumber(String phone) {
    return employeeRepository.existsByPhone(phone);
  }

  @CachePut(value = "employee", key = "#request.id")
  public Employee updateEmployeeById(EmployeeUpdateRequest request) {
    return employeeRepository.findEmployeeById(request.getId()).map(employee -> {
      if (StringUtils.isNotEmpty(request.getName())) {
        employee.setName(request.getName());
      }
      if (StringUtils.isNotEmpty(request.getMail())) {
        employee.setMail(request.getMail());
      }
      if (StringUtils.isNotEmpty(request.getAddress())) {
        employee.setAddress(request.getAddress());
      }
      if (StringUtils.isNotEmpty(request.getPassword())) {
        employee.setPassword(request.getPassword());
      }
      employee.setLastModifiedDate(Date.from(Instant.now()));
      return employeeRepository.save(employee);
    }).orElseThrow(() -> exception(EntityType.EMPLOYEE, ExceptionType.ENTITY_NOT_FOUND,
        String.valueOf(request.getId())));
  }

  public Page<Employee> getAllEmployee(EmployeeListRequest pageRequest) {
    String[] sortSplit = pageRequest.getSort().split(",");
    if (!Objects.isNull(pageRequest.getSearch())) {
      return employeeRepository
          .findAll(EmployeeSpecification.textInAllColumns(pageRequest.getSearch()),
              new org.springframework.data.domain.PageRequest(pageRequest.getPage(),
                  pageRequest.getSize(),
                  (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                      : Sort.Direction.DESC), sortSplit[0]));
    } else {
      return employeeRepository
          .findAll(employeeSpecification.getFilter(pageRequest),
              new org.springframework.data.domain.PageRequest(pageRequest.getPage(),
                  pageRequest.getSize(),
                  (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                      : Sort.Direction.DESC), sortSplit[0]));
    }
  }

  @Cacheable(value = "employee", key = "#id", unless = "#result != null")
  public Employee findEmployeeById(Integer id) {
    return employeeRepository.findEmployeeById(id)
        .orElseThrow(() -> exception(EntityType.EMPLOYEE, ExceptionType.ENTITY_NOT_FOUND,
            String.valueOf(id)));
  }

  @CacheEvict(value = "employee", key = "#id", condition = "#status.id == 3")
  public Employee changeStatus(Integer id, EnumStatus status) {
    return employeeRepository.findEmployeeById(id).map(employee -> {
      employee.setStatus(status.getId());
      return employeeRepository.save(employee);
    }).orElseThrow(
        () -> exception(EntityType.EMPLOYEE, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(id)));
  }

  public void logoutUser(int id) {
    RefreshToken refreshToken = refreshTokenService.findByEmployeeId(id);
    log.info("Removing refresh token associated with id [" + id + "]");
    refreshTokenService.deleteById(refreshToken.getId());
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
