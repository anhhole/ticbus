package com.ticbus.backend.controller.mobile;

import com.ticbus.backend.model.Trip;
import com.ticbus.backend.payload.request.TripListRequest;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.services.TripService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile/trips")
@Slf4j
@Api(value = "Mobile Trip Controller", description = "Operations pertaining to Trip")
public class TripController {

  @Autowired
  private TripService tripService;

  @ApiOperation(value = "Get All Trip Information", authorizations = {
      @Authorization(value = "Bearer")})
  @GetMapping("")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<GetArrayResponse<Trip>> getAllTripInformation(TripListRequest pageRequest) {
    log.info("START: get all employees");
    GetArrayResponse<Trip> response = new GetArrayResponse<>();
    Page<Trip> page = tripService.getAllTripInformation(pageRequest);
    response.setSuccess(page.getContent(), page.getTotalElements());
    log.info("END: get all employees");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Find Trip By Id", authorizations = {@Authorization(value = "Bearer")})
  @GetMapping("/{tripId}")
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
