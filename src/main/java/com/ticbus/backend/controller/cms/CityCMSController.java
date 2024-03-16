package com.ticbus.backend.controller.cms;

import com.ticbus.backend.model.City;
import com.ticbus.backend.payload.request.CityChangeStatusRequest;
import com.ticbus.backend.payload.request.CityListRequest;
import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.repository.CityRepository;
import com.ticbus.backend.repository.DepartureRepository;
import com.ticbus.backend.services.CityService;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/cms")
public class CityCMSController {

  @Autowired
  private CityService cityService;

  @Autowired
  private CityRepository cityRepository;

  @Autowired
  private DepartureRepository departureRepository;

  @ApiOperation(value = "Get all cities")
  @GetMapping("/cities")
  public ResponseEntity<GetArrayResponse<City>> getAllEmployee(
      @Valid CityListRequest request) {
    log.info("START: get all cities");
    GetArrayResponse<City> response = new GetArrayResponse<>();
    Page<City> page = cityService.getAllCities(request);
    response.setSuccess(page.getContent(), page.getNumberOfElements());
    log.info("END: get all cities");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Find city by cityId")
  @GetMapping("/cities/{cityId}")
  public ResponseEntity<GetSingleItemResponse<City>> findEmployeeById(
      @PathVariable(name = "cityId") Integer id) {
    log.info("START: find city by id: " + id);
    GetSingleItemResponse<City> response = new GetSingleItemResponse<>();
    City city = cityService.findCityById(id);
    response.setSuccess(city);
    log.info("END: find city by id");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Change status By EmployeeId")
  @PostMapping("/cities/{cityId}")
  public ResponseEntity<BaseResponse> changeStatusCityById(
      @PathVariable(name = "cityId") Integer id,
      @RequestBody @Valid CityChangeStatusRequest request) {
    BaseResponse response = new BaseResponse();
    response.setFailed();
    log.info("START: deactive city: " + id);
    Optional<City> city = Optional.ofNullable(cityService.changeStatus(id, request.getStatus()));
    if (city.isPresent()) {
      response.setSuccess();
    }
    log.info("END: deactive city");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Set City Index")
  @PostMapping("/cities/{cityId}/index")
  public ResponseEntity<BaseResponse> settingIndexWithCityId(
      @PathVariable(name = "cityId") Integer cityId, @RequestParam(name = "index")
      Integer index) {
    BaseResponse response = new BaseResponse();
    response.setFailed();
    log.info("[START]: Setting Index With CityId: " + cityId);
    Optional<City> optCity = Optional
        .ofNullable(cityService.settingIndexWithCityId(cityId, index));
    if (optCity.isPresent()) {
      response.setSuccess();
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    log.info("[END]: Setting Index With CityId");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ApiOperation(value = "Update City Index")
  @PutMapping("/cities/{cityId}/index")
  public ResponseEntity<BaseResponse> updateIndexByCityId(
      @PathVariable(name = "cityId") Integer cityId, @RequestParam(name = "index")
      Integer index) {
    log.info("[START]: Updating Index With CityId: " + cityId);
    BaseResponse response = new BaseResponse();
    response.setFailed();
    Optional<City> optCity = Optional
        .ofNullable(cityService.updateIndexByCityId(cityId, index));
    if (optCity.isPresent()) {
      response.setSuccess();
    }
    log.info("[END]: Updating Index With CityId");
    return ResponseEntity.ok(response);
  }
}
