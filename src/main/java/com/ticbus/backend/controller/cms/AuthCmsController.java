package com.ticbus.backend.controller.cms;

import com.ticbus.backend.exception.EntityType;
import com.ticbus.backend.exception.ExceptionType;
import com.ticbus.backend.exception.ResourceNotFoundException;
import com.ticbus.backend.exception.TicbusException;
import com.ticbus.backend.model.Employee;
import com.ticbus.backend.model.RefreshToken;
import com.ticbus.backend.payload.request.LoginRequest;
import com.ticbus.backend.payload.request.TokenRefreshRequest;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.payload.response.JwtAuthenticationResponse;
import com.ticbus.backend.repository.EmployeeRepository;
import com.ticbus.backend.security.JwtTokenProvider;
import com.ticbus.backend.services.RefreshTokenService;
import com.ticbus.backend.util.NetworkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AnhLH
 */
@RestController
@RequestMapping("/cms/auth")
@Log4j2
@Api(value = "Employee Authen System", description = "CMS Employee authen")
public class AuthCmsController {

  @Autowired
  private final PasswordEncoder passwordEncoder;
  @Autowired

  private final AuthenticationManager authenticationManager;
  @Autowired
  private final JwtTokenProvider tokenProvider;
  @Autowired
  private final EmployeeRepository employeeRepository;
  @Autowired
  private final RefreshTokenService refreshTokenService;

  public AuthCmsController(
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      JwtTokenProvider tokenProvider,
      EmployeeRepository employeeRepository,
      RefreshTokenService refreshTokenService) {
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.employeeRepository = employeeRepository;
    this.refreshTokenService = refreshTokenService;
  }

  /**
   * @param request phone, password
   * @return token
   */
  @PostMapping("/login")
  public ResponseEntity<GetSingleItemResponse> login(@Valid @RequestBody LoginRequest loginRequest,
      HttpServletRequest request) {
    GetSingleItemResponse<JwtAuthenticationResponse> res = new GetSingleItemResponse<>();
    // Create user de
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getPhone(),
            loginRequest.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String ip = NetworkUtils.getClientIpAddr(request);
    String jwt = tokenProvider.generateCMSToken(authentication);
    try {
      Optional<Employee> employee = Optional
          .ofNullable(employeeRepository.findByPhone(loginRequest.getPhone()).map(employee1 -> {
            employee1.setLastIP(ip);
            employee1.setLastLoginTime(Date.from(Instant.now()));
            return employeeRepository.save(employee1);
          }).orElseThrow(
              () -> new ResourceNotFoundException("Employee", "not found phone", loginRequest)));
      if (employee.isPresent()) {
        if (employee.get().getStatus() == 1) {
          refreshTokenService.deleteByEmployeeId(employee.get().getId());
          RefreshToken refreshToken = refreshTokenService.createRefreshToken(employee.get());
          refreshTokenService.save(refreshToken);
          res.setSuccess(new JwtAuthenticationResponse(jwt, refreshToken.getToken(),
              tokenProvider.getExpiryDuration()));
        } else {
          res.setUserNotEnable();
        }
      }
    } catch (Exception e) {
      log.error("Failed to update employee record login: " + e.getMessage());
      res.setFailed(e.getMessage());
    }
    return ResponseEntity.ok(res);
  }

  /**
   * Refresh the expired jwt token using a refresh token for the specific device and return a new
   * token to the caller
   */
  @PostMapping("/refresh")
  @ApiOperation(value =
      "Refresh the expired jwt authentication by issuing a token refresh request and returns the" +
          "updated response tokens")
  public ResponseEntity refreshJwtToken(
      @ApiParam(value = "The TokenRefreshRequest payload") @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
    String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
    Optional<String> updatedToken = Optional.of(refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshToken -> {
          refreshTokenService.verifyExpiration(refreshToken);
          refreshTokenService.increaseCount(refreshToken);
          return refreshToken;
        })
        .map(RefreshToken::getEmployee)
        .map(employee -> tokenProvider.generateToken(employee.getPhone(), employee.getId()))
        .orElseThrow(() -> exception(EntityType.REFRESH_TOKEN, ExceptionType.ENTITY_EXCEPTION,
            requestRefreshToken)));
    return updatedToken.map(updatedToken1 -> {
      String refreshToken = tokenRefreshRequest.getRefreshToken();
      log.info("Created new Jwt Auth token: " + updatedToken1);
      return ResponseEntity.ok(new JwtAuthenticationResponse(updatedToken1, refreshToken,
          tokenProvider.getExpiryDuration()));
    }).orElseThrow(() -> exception(EntityType.REFRESH_TOKEN, ExceptionType.ENTITY_EXCEPTION,
        requestRefreshToken));
  }

  /**
   * Returns a new RuntimeException
   */
  private RuntimeException exception(EntityType entityType, ExceptionType exceptionType,
      String... args) {
    return TicbusException.throwException(entityType, exceptionType, args);
  }
}
