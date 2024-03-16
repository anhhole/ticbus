package com.ticbus.backend.controller.mobile;

import com.ticbus.backend.common.CommonDefs;
import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.exception.ResourceNotFoundException;
import com.ticbus.backend.model.Customer;
import com.ticbus.backend.payload.request.CreateOTPRequest;
import com.ticbus.backend.payload.request.LoginRequest;
import com.ticbus.backend.payload.request.OTPRequest;
import com.ticbus.backend.payload.request.RegisterRequest;
import com.ticbus.backend.payload.request.SendOTP;
import com.ticbus.backend.payload.request.VerifyOTPRequest;
import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.payload.response.JwtAuthenticationMobileResponse;
import com.ticbus.backend.payload.response.OTPCreateResponse;
import com.ticbus.backend.payload.response.OTPResponse;
import com.ticbus.backend.repository.CustomerRepository;
import com.ticbus.backend.security.JwtTokenProvider;
import com.ticbus.backend.util.JSONUtils;
import com.ticbus.backend.util.NetworkUtils;
import com.ticbus.backend.util.PhoneUtils;
import io.swagger.annotations.Api;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AnhLH
 */
@RestController
@RequestMapping("/mobile/auth")
@Log4j2
@Api(value = "Mobile Authen System", description = "Operations pertaining to Mobile Authen")
public class AuthController {

  @Autowired
  private final CustomerRepository customerRepository;
  @Autowired
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private final AuthenticationManager authenticationManager;
  @Autowired
  private final JwtTokenProvider tokenProvider;
  @Value("${app.debug}")
  private boolean debug;

  public AuthController(CustomerRepository customerRepository,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      JwtTokenProvider tokenProvider) {
    this.customerRepository = customerRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
  }


  /**
   * @param request name, phone, pass, mail , address
   * @return status
   */
  @PostMapping("/register")
  public ResponseEntity<BaseResponse> register(@Valid @RequestBody RegisterRequest request) {
    BaseResponse res = new BaseResponse();
    // Check Phone is Valid
    if (!PhoneUtils.getInstance().isValidPhone(request.getPhone())) {
      res.setPhoneInvalid();
      return ResponseEntity.ok(res);
    }
    Optional<Customer> customerOptional = customerRepository.findByPhone(request.getPhone());
    if (customerOptional.isPresent()) {
      if (customerOptional.get().getIsActive() && customerOptional.get().getStatus() == 1) {
        res.setPhoneDup();
        return ResponseEntity.ok(res);
      } else {
        customerOptional.get().setName(request.getName());
        customerOptional.get().setAddress(request.getAddress());
        customerOptional.get().setMail(request.getMail());
        customerOptional.get().setPassword(passwordEncoder.encode(request.getPassword()));
        customerOptional.get().setStatus(EnumStatus.ENABLE.getId());
        customerOptional.get().setCreatedTime(Date.from(Instant.now()));
        customerOptional.get().setLastLoginTime(Date.from(Instant.now()));
        customerRepository.save(customerOptional.get());
        res.setSuccess();
        return ResponseEntity.ok(res);
      }
    }
    Customer customer = new Customer();
    customer.setName(request.getName());
    customer.setAddress(request.getAddress());
    customer.setMail(request.getMail());
    customer.setPassword(passwordEncoder.encode(request.getPassword()));
    customer.setPhone(request.getPhone());
    customer.setStatus(EnumStatus.ENABLE.getId());
    customer.setCreatedTime(Date.from(Instant.now()));
    customer.setLastLoginTime(Date.from(Instant.now()));
    customer.setIsActive(false);
    try {
      customerRepository.save(customer);
      res.setSuccess();
    } catch (Exception e) {
      log.error("Server Error . Can not update " + customer.getPhone() + " to db right now!!!" + e
          .getMessage());
      res.setServerError();
    }
    return ResponseEntity.ok(res);
  }

  /**
   * @param request phone, password
   * @return token
   */
  @PostMapping("/login")
  public ResponseEntity<GetSingleItemResponse> login(@Valid @RequestBody LoginRequest loginRequest,
      HttpServletRequest request) {
    GetSingleItemResponse<JwtAuthenticationMobileResponse> res = new GetSingleItemResponse<>();
    // Create user de
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getPhone(),
            loginRequest.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String ip = NetworkUtils.getClientIpAddr(request);
    String jwt = tokenProvider.generateToken(authentication);
    try {
      customerRepository.findByPhoneAndIsActive(loginRequest.getPhone(), true).map(customer -> {
        customer.setLastIP(ip);
        customer.setLastLoginTime(Date.from(Instant.now()));
        return customerRepository.save(customer);
      }).orElseThrow(
          () -> new ResourceNotFoundException("customer", "not found phone", loginRequest));
      Optional<Customer> customer = customerRepository
          .findByPhoneAndIsActive(loginRequest.getPhone(), true);
      if (customer.isPresent()) {
        if (customer.get().getStatus() == 1) {
          res.setSuccess(new JwtAuthenticationMobileResponse(jwt));
        } else {
          res.setUserNotEnable();
        }
      }
    } catch (Exception e) {
      log.error("Failed to update customer record login: " + e.getMessage());
      res.setFailed(e.getMessage());
    }
    return ResponseEntity.ok(res);
  }

  @PostMapping("/sendOTP")
  public ResponseEntity<BaseResponse> sendOTP(@RequestBody SendOTP otpRequest)
      throws IOException {
    BaseResponse res = new BaseResponse();
    OkHttpClient client = new OkHttpClient();
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    CreateOTPRequest postJson = new CreateOTPRequest();
    postJson.setApp_id(CommonDefs.OTP_APP_ID);
    postJson.setContent(CommonDefs.CONTENT_CREATE_OTP);
    postJson.setTo(otpRequest.getPhone());
    okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, JSONUtils.Serialize(postJson));
    String credential = Credentials.basic(CommonDefs.OTP_API_ACCESS_TOKEN, ":x");
    Request request = new Builder().addHeader("Authorization", credential)
        .url(CommonDefs.URL_CREATE_OTP).post(body).build();
    Response response = client.newCall(request).execute();
    OTPCreateResponse otpResponse = JSONUtils
        .DeSerialize(response.body().string(), OTPCreateResponse.class);
    if (otpResponse.getStatus().equals("success")) {
      res.setSuccess();
    } else {
      res.setFailed(otpResponse.getMessage());
    }
    return ResponseEntity.ok(res);
  }

  @PostMapping("/verifyOTP")
  public ResponseEntity<GetSingleItemResponse<JwtAuthenticationMobileResponse>> verify(
      @RequestBody OTPRequest otpRequest)
      throws IOException {
    GetSingleItemResponse<JwtAuthenticationMobileResponse> res = new GetSingleItemResponse<>();
    OkHttpClient client = new OkHttpClient();
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Optional<Customer> customerOptional = customerRepository
        .findByPhoneAndStatus(otpRequest.getPhone(), 1);
    String jwt = "";
    if (customerOptional.isPresent()) {
      jwt = tokenProvider
          .generateToken(customerOptional.get().getPhone(), customerOptional.get().getId());
    } else {
      res.setVerifyFailed();
      return ResponseEntity.ok(res);
    }
    if (debug && otpRequest.getOtp().equals("1234")) {
      res.setSuccess(new JwtAuthenticationMobileResponse(jwt));
      customerRepository.findByPhone(otpRequest.getPhone()).map(customer -> {
        customer.setIsActive(true);
        return customerRepository.save(customer);
      });
      return ResponseEntity.ok(res);
    } else {
      VerifyOTPRequest postJson = new VerifyOTPRequest();
      postJson.setApp_id(CommonDefs.OTP_APP_ID);
      postJson.setPin_code(otpRequest.getOtp());
      postJson.setPhone(otpRequest.getPhone());
      okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, JSONUtils.Serialize(postJson));
      String credential = Credentials.basic(CommonDefs.OTP_API_ACCESS_TOKEN, ":x");
      Request request = new Builder().addHeader("Authorization", credential)
          .url(CommonDefs.URL_VERIFY_OTP).post(body).build();
      Response response = client.newCall(request).execute();
      OTPResponse otpResponse = JSONUtils.DeSerialize(response.body().string(), OTPResponse.class);
      if (otpResponse.getStatus().equals("success") && otpResponse.getData().getPhone()
          .equals(otpRequest.getPhone())
          && otpResponse.getData().getPin().equals(otpRequest.getOtp())
          && otpResponse.getData().getVerified()) {
        res.setSuccess(new JwtAuthenticationMobileResponse(jwt));
        customerRepository.findByPhoneAndStatus(otpRequest.getPhone(), 1).map(customer -> {
          customer.setIsActive(true);
          return customerRepository.save(customer);
        });
      } else {
        res.setVerifyFailed();
      }
      return ResponseEntity.ok(res);
    }
  }

  @PostMapping("forgotPasswordVerifyOtp")
  public ResponseEntity<GetSingleItemResponse<JwtAuthenticationMobileResponse>> verifyOtpPass(
      @Valid @RequestBody OTPRequest otpRequest) throws IOException {
    GetSingleItemResponse<JwtAuthenticationMobileResponse> res = new GetSingleItemResponse<>();
    OkHttpClient client = new OkHttpClient();
    String jwt;
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Optional<Customer> customerOptional = customerRepository.findByPhone(otpRequest.getPhone());
    if (customerOptional.isPresent()) {
      if (customerOptional.get().getStatus() != 1) {
        res.setUserNotEnable();
        return ResponseEntity.ok(res);
      }
      jwt = tokenProvider
          .generateToken(customerOptional.get().getPhone(), customerOptional.get().getId());
    } else {
      res.setVerifyFailed();
      return ResponseEntity.ok(res);
    }
    if (debug && otpRequest.getOtp().equals("1234")) {
      res.setSuccess(new JwtAuthenticationMobileResponse(jwt));
      return ResponseEntity.ok(res);
    } else {
      VerifyOTPRequest postJson = new VerifyOTPRequest();
      postJson.setApp_id(CommonDefs.OTP_APP_ID);
      postJson.setPin_code(otpRequest.getOtp());
      postJson.setPhone(otpRequest.getPhone());
      okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, JSONUtils.Serialize(postJson));
      String credential = Credentials.basic(CommonDefs.OTP_API_ACCESS_TOKEN, ":x");
      Request request = new Builder().addHeader("Authorization", credential)
          .url(CommonDefs.URL_VERIFY_OTP).post(body).build();
      Response response = client.newCall(request).execute();
      OTPResponse otpResponse = JSONUtils.DeSerialize(response.body().string(), OTPResponse.class);
      if (otpResponse.getStatus().equals("success") && otpResponse.getData().getPhone()
          .equals(otpRequest.getPhone())
          && otpResponse.getData().getPin().equals(otpRequest.getOtp())
          && otpResponse.getData().getVerified()) {

        if (customerOptional.get().getStatus() != 1) {
          res.setUserNotEnable();
          return ResponseEntity.ok(res);
        }
        jwt = tokenProvider
            .generateToken(customerOptional.get().getPhone(), customerOptional.get().getId());
        res.setSuccess(new JwtAuthenticationMobileResponse(jwt));

      } else {
        res.setVerifyFailed();
      }
      return ResponseEntity.ok(res);
    }
  }

  @PostMapping("/deleteUser/{phone}")
  public ResponseEntity<BaseResponse> deleteUser(@PathVariable("phone") String phone) {
    BaseResponse res = new BaseResponse();
    Optional<Customer> customerOptional = customerRepository.findByPhone(phone);
    if (customerOptional.isPresent()) {
      customerRepository.delete(customerOptional.get());
    }
    res.setSuccess();
    return ResponseEntity.ok(res);
  }
}

