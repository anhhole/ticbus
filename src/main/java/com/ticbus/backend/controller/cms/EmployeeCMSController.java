package com.ticbus.backend.controller.cms;

import com.ticbus.backend.common.enumeration.EnumRole;
import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.model.Employee;
import com.ticbus.backend.payload.request.EmployeeChangeStatusRequest;
import com.ticbus.backend.payload.request.EmployeeListRequest;
import com.ticbus.backend.payload.request.EmployeeRequest;
import com.ticbus.backend.payload.request.EmployeeUpdateRequest;
import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.payload.response.GetMeEmployeeResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.repository.EmployeeRepository;
import com.ticbus.backend.security.JwtTokenProvider;
import com.ticbus.backend.services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AnhLH
 */
@RestController
@RequestMapping("/cms")
@Slf4j
@Api(value = "CMS Employee Api", description = "CMS Employee API ")
public class EmployeeCMSController {

  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private JwtTokenProvider tokenProvider;
  @Autowired
  private EmployeeRepository employeeRepository;

  @ApiOperation(value = "Create New Employee", authorizations = {
      @Authorization(value = "Bearer")})
  @PostMapping("/employees")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<BaseResponse> createNewEmployee(
      @RequestBody @Valid EmployeeRequest request) {
    log.info("[START]: create new employee");
    BaseResponse response = new BaseResponse();
    response.setFailed();
    Optional<Employee> employeeOptional = Optional
        .ofNullable(employeeService.createNewEmployee(request));
    if (employeeOptional.isPresent()) {
      response.setSuccess();
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    log.info("[END]: create new employee");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ApiOperation(value = "Update Employee By EmployeeId", authorizations = {
      @Authorization(value = "Bearer")})
  @PutMapping("/employees")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<BaseResponse> updateEmployeeById(
      @RequestBody @Valid EmployeeUpdateRequest request) {
    BaseResponse response = new BaseResponse();
    response.setFailed();
    log.info("[START]: update employee by id: " + request.getId());
    Optional<Employee> employeeOptional = Optional
        .ofNullable(employeeService.updateEmployeeById(request));
    if (employeeOptional.isPresent()) {
      response.setSuccess();
    }
    log.info("[END]: update employee");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Get all employees", authorizations = {@Authorization(value = "Bearer")})
  @GetMapping("/employees")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<GetArrayResponse<Employee>> getAllEmployee(
      @Valid EmployeeListRequest request) {
    log.info("START: get all employees");
    GetArrayResponse<Employee> response = new GetArrayResponse<>();
    Page<Employee> page = employeeService.getAllEmployee(request);
    response.setSuccess(page.getContent(), page.getTotalElements());
    log.info("END: get all employees");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Find employee by EmployeeId", authorizations = {
      @Authorization(value = "Bearer")})
  @GetMapping("/employees/{employeeId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<GetSingleItemResponse<Employee>> findEmployeeById(
      @PathVariable(name = "employeeId") Integer id) {
    log.info("START: find employee by id: " + id);
    GetSingleItemResponse<Employee> response = new GetSingleItemResponse<>();
    Employee employee = employeeService.findEmployeeById(id);
    response.setSuccess(employee);
    log.info("END: find employee by id");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Change status By EmployeeId", authorizations = {
      @Authorization(value = "Bearer")})
  @PostMapping("/employees/{employeeId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<BaseResponse> changeStatusEmployeeById(
      @PathVariable(name = "employeeId") Integer id, EmployeeChangeStatusRequest request) {
    BaseResponse response = new BaseResponse();
    response.setFailed();
    log.info("START: deactive employee: " + id);
    Optional<Employee> employee = Optional
        .ofNullable(employeeService.changeStatus(id, EnumStatus.getById(request.getStatus())));
    if (employee.isPresent()) {
      response.setSuccess();
    }
    log.info("END: deactive employee");
    return ResponseEntity.ok(response);
  }

  @PostMapping("/logout")
  @ApiOperation(value = "Logs the specified user device and clears the refresh tokens associated with it", authorizations = {
      @Authorization(value = "Bearer")})
  public ResponseEntity logoutUser(@RequestHeader("Authorization") String token) {
    BaseResponse res = new BaseResponse();
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    } else {
      res.setItemNotFound();
      return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    int employeeId = tokenProvider.getIdFromToken(token);
    employeeService.logoutUser(employeeId);
    res.setSuccess();
    return ResponseEntity.ok(res);
  }

  @GetMapping("/getMe")
  @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
  public ResponseEntity<GetSingleItemResponse<GetMeEmployeeResponse>> getMe(
      @RequestHeader("Authorization") String token) {
    GetSingleItemResponse<GetMeEmployeeResponse> res = new GetSingleItemResponse<>();
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    } else {
      res.setItemNotFound();
      return ResponseEntity.ok(res);
    }
    Integer userId = tokenProvider.getIdFromToken(token);
    try {
      GetMeEmployeeResponse employeeResponse = new GetMeEmployeeResponse();
      Optional<Employee> employeeOptional = employeeRepository.findById(userId);
      if (employeeOptional.isPresent()) {
        employeeResponse.setId(userId);
        employeeResponse.setName(employeeOptional.get().getName());
        employeeResponse.setPhone(employeeOptional.get().getPhone());
        employeeResponse.setMail(employeeOptional.get().getMail());
        employeeResponse.setTimeExpired(tokenProvider.getExDate(token));
        employeeResponse.setRole(EnumRole.getById(employeeOptional.get().getRole()));
        res.setSuccess(employeeResponse);
      } else {
        res.setItemNotFound();
      }
    } catch (Exception e) {
      log.error("[Customer]- Error to get record from DB");
      res.setFailed(e.getMessage());
    }
    return ResponseEntity.ok(res);
  }
}
