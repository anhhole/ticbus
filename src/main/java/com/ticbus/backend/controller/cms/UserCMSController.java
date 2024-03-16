package com.ticbus.backend.controller.cms;

import com.ticbus.backend.model.Customer;
import com.ticbus.backend.payload.request.CustomerListRequest;
import com.ticbus.backend.payload.request.CustomerUpdateRequest;
import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/users")
@Log4j2
@Api(value = "CMS User Api", description = "CMS User API ")
public class UserCMSController {

  @Autowired
  private UserService userService;

  @GetMapping("/{userId}")
  @ApiOperation(value = "Get User By Id", authorizations = {@Authorization(value = "Bearer")})
  public ResponseEntity<GetSingleItemResponse<Customer>> findUserById(
      @PathVariable(value = "userId") Integer id) {
    log.info("[START]: Find User By Id: " + id);
    GetSingleItemResponse<Customer> response = new GetSingleItemResponse<Customer>();
    Customer customer = userService.findUserById(id);
    response.setSuccess(customer);
    log.info("[END]: Find User By Id");
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Change user status", authorizations = {
      @Authorization(value = "Bearer")})
  public ResponseEntity<BaseResponse> changeUserStatus(@PathVariable("id") int id,
      CustomerUpdateRequest request) {
    log.info("[START]: change employee status");
    BaseResponse response = new BaseResponse();
    response.setFailed();
    Optional<Customer> optionalCustomer = Optional.ofNullable(userService.changeUserStatus(request, id));
    if (optionalCustomer.isPresent()) {
      response.setSuccess();
    }
    log.info("[END]: change employee status");
    return ResponseEntity.ok(response);
  }

  @GetMapping()
  @ApiOperation(value = "Get All Users", authorizations = {@Authorization(value = "Bearer")})
  public ResponseEntity<GetArrayResponse<Customer>> getAllUsers(
      @Valid CustomerListRequest request) {
    log.info("[START]: Get All Users");
    GetArrayResponse<Customer> response = new GetArrayResponse<Customer>();
    Page<Customer> page = userService.getAllCustomers(request);
    response.setSuccess(page.getContent(), page.getTotalElements());
    log.info("[END]: Get All Users");
    return ResponseEntity.ok(response);
  }

}
