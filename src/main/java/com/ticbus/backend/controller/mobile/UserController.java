package com.ticbus.backend.controller.mobile;

import com.ticbus.backend.model.Customer;
import com.ticbus.backend.payload.request.ForgotPassRequest;
import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.payload.response.GetMeCustomerResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.repository.CustomerRepository;
import com.ticbus.backend.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile/user")
@Log4j2
@Api(value = "Mobile User Api", description = "Mobile User API ")
public class UserController {

  @Autowired
  private final CustomerRepository customerRepository;
  @Autowired
  private final JwtTokenProvider tokenProvider;
  @Autowired
  private final PasswordEncoder passwordEncoder;

  public UserController(CustomerRepository customerRepository, JwtTokenProvider tokenProvider,
      PasswordEncoder passwordEncoder) {
    this.customerRepository = customerRepository;
    this.tokenProvider = tokenProvider;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/changePassword")
  @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
  public ResponseEntity<BaseResponse> changePassword(
      @RequestBody ForgotPassRequest forgotPassRequest,
      @RequestHeader("Authorization") String token) {
    BaseResponse res = new BaseResponse();
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    } else {
      res.setItemNotFound();
      return ResponseEntity.ok(res);
    }
    String phone = tokenProvider.getPhoneFromJWT(token);
    try {
      customerRepository.findByPhone(phone).map(customer -> {
        customer.setPassword(passwordEncoder.encode(forgotPassRequest.getNewPass()));
        return customerRepository.save(customer);
      });
      res.setSuccess();
    } catch (Exception e) {
      log.error(e.getMessage());
      res.setFailed();
    }
    return ResponseEntity.ok(res);
  }

  @GetMapping
  @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
  public ResponseEntity<GetSingleItemResponse<GetMeCustomerResponse>> getMe(
      @RequestHeader("Authorization") String token) {
    GetSingleItemResponse<GetMeCustomerResponse> res = new GetSingleItemResponse<>();
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    } else {
      res.setItemNotFound();
      return ResponseEntity.ok(res);
    }
    Integer userId = tokenProvider.getIdFromToken(token);
    try {
      GetMeCustomerResponse customerResponse = new GetMeCustomerResponse();
      Optional<Customer> customerOptional = customerRepository.findById(userId);
      if (customerOptional.isPresent()) {
        customerResponse.setId(userId);
        customerResponse.setName(customerOptional.get().getName());
        customerResponse.setPhone(customerOptional.get().getPhone());
        customerResponse.setAvatar(customerOptional.get().getAvatar());
        res.setSuccess(customerResponse);
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
