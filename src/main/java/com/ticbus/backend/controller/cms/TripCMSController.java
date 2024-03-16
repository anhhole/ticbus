package com.ticbus.backend.controller.cms;

import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.model.Trip;
import com.ticbus.backend.payload.request.TripChangeStatusRequest;
import com.ticbus.backend.payload.request.TripListRequest;
import com.ticbus.backend.payload.request.TripRequest;
import com.ticbus.backend.payload.request.TripUpdateRequest;
import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.services.TripService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms")
@Slf4j
public class TripCMSController {

  @Autowired
  private TripService tripService;

  @ApiOperation(value = "Create New Trip", authorizations = {@Authorization(value = "Bearer")})
  @PostMapping("/trips")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<BaseResponse> createNewTrip(@RequestBody TripRequest request) {
    log.info("[START]: create new trip");
    BaseResponse response = new BaseResponse();
    response.setFailed();
    Optional<Trip> employeeOptional = Optional
        .ofNullable(tripService.createNewTrip(request));
    if (employeeOptional.isPresent()) {
      response.setSuccess();
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    log.info("[END]: create new trip");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ApiOperation(value = "Get All Trip Information", authorizations = {
      @Authorization(value = "Bearer")})
  @GetMapping("/trips")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<GetArrayResponse<Trip>> getAllTripInformation(TripListRequest pageRequest) {
    log.info("START: get all employees");
    GetArrayResponse<Trip> response = new GetArrayResponse<>();
    Page<Trip> page = tripService.getAllTripInformation(pageRequest);
    response.setSuccess(page.getContent(), page.getTotalElements());
    log.info("END: get all employees");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Update Trip Information", authorizations = {
      @Authorization(value = "Bearer")})
  @PutMapping("/trips")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<BaseResponse> updateTripById(
      @RequestBody @Valid TripUpdateRequest request) {
    log.info("[START]: update trip information");
    BaseResponse response = new BaseResponse();
    response.setFailed();
    Optional<Trip> tripOptional = Optional
        .ofNullable(tripService.updateTripById(request));
    if (tripOptional.isPresent()) {
      response.setSuccess();
    }
    log.info("[END]: update trip information");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Change Trip Status", authorizations = {@Authorization(value = "Bearer")})
  @PostMapping("/trips/{tripId}/")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<BaseResponse> changeStatusTripById(
      @PathVariable(name = "tripId") Integer id, TripChangeStatusRequest request) {
    log.info("[START]: Change Trip Status");
    BaseResponse response = new BaseResponse();
    response.setFailed();
    Optional<Trip> tripOptional = Optional
        .ofNullable(tripService.changeStatus(id, EnumStatus.getById(request.getStatus())));
    if (tripOptional.isPresent()) {
      response.setSuccess();
    }
    log.info("[END]: Change Trip Status");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Find Trip By Id", authorizations = {@Authorization(value = "Bearer")})
  @GetMapping("/trips/{tripId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<GetSingleItemResponse<Trip>> findTripById(
      @PathVariable(name = "tripId") Integer id) {
    log.info("[START]: find Trip by Id: " + id);
    GetSingleItemResponse<Trip> response = new GetSingleItemResponse<Trip>();
    Trip trip = tripService.findTripById(id);
    response.setSuccess(trip);
    log.info("[END]: find Trip by Id");
    return ResponseEntity.ok(response);
  }
}
