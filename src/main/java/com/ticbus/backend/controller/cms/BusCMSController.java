package com.ticbus.backend.controller.cms;

import com.ticbus.backend.model.Bus;
import com.ticbus.backend.payload.request.BusListRequest;
import com.ticbus.backend.payload.request.BusRequest;
import com.ticbus.backend.payload.request.BusUpdateRequest;
import com.ticbus.backend.payload.request.PageRequest;
import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.services.BusService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/buses")
@Slf4j
@Api(value = "CMS BUS Api", description = "CMS BUS Api")
public class BusCMSController {

  @Autowired
  private BusService busService;

  @ApiOperation(value = "Create New Bus", authorizations = {@Authorization(value = "Bearer")})
  @PostMapping
  public ResponseEntity<BaseResponse> createNewBus(@RequestBody BusRequest request) {
    log.info("[START]: create new bus");
    BaseResponse response = new BaseResponse();
    response.setFailed();
    Optional<Bus> busOptional = Optional.ofNullable(busService.createNewBus(request));
    if (busOptional.isPresent()) {
      response.setSuccess();
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    log.info("[END]: create new bus");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ApiOperation(value = "Update Bus By License Plates", authorizations = {
      @Authorization(value = "Bearer")})
  @PutMapping
  public ResponseEntity<BaseResponse> updateBus(@RequestBody BusUpdateRequest request) {
    log.info("[START]: update bus");
    BaseResponse response = new BaseResponse();
    response.setFailed();
    Optional<Bus> busOptional = Optional.ofNullable(busService.updateBus(request));
    if (busOptional.isPresent()) {
      response.setSuccess();
    }
    log.info("[END]: update bus");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Get all buses", authorizations = {@Authorization(value = "Bearer")})
  @GetMapping
  public ResponseEntity<GetArrayResponse<Bus>> getAllBuses(@Valid BusListRequest request) {
    log.info("[START]: Get all buses");
    GetArrayResponse<Bus> response = new GetArrayResponse<>();
    Page<Bus> page = busService.getAllBuses(request);
    response.setSuccess(page.getContent(), page.getTotalElements());
    log.info("[END]: Get all buses");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Find Bus By License Plate", authorizations = {
      @Authorization(value = "Bearer")})
  @PostMapping("/{licensePlate}")
  public ResponseEntity<GetSingleItemResponse<Bus>> findBusByLicensePlate(
      @PathVariable(name = "licensePlate") String licensePlate) {
    log.info("[START]: Find Bus By License Plate: " + licensePlate);
    GetSingleItemResponse<Bus> response = new GetSingleItemResponse<Bus>();
    Bus bus = busService.findBusByLicensePlate(licensePlate);
    response.setSuccess(bus);
    log.info("[END]: Find Bus By License Plate");
    return ResponseEntity.ok(response);
  }

}
