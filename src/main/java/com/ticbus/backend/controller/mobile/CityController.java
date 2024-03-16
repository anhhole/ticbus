package com.ticbus.backend.controller.mobile;

import com.ticbus.backend.model.City;
import com.ticbus.backend.model.Departure;
import com.ticbus.backend.model.Destination;
import com.ticbus.backend.payload.request.CityListRequest;
import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.services.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile/cities")
@Slf4j
@Api(value = "Mobile City Controller", description = "Operations pertaining to City")
public class CityController {

  @Autowired
  private CityService cityService;

  @ApiOperation(value = "Get all cities")
  @GetMapping("")
  public ResponseEntity<GetArrayResponse<City>> getAllCity(
      @Valid CityListRequest request) {
    log.info("START: get all cities");
    GetArrayResponse<City> response = new GetArrayResponse<>();
    Page<City> page = cityService.getAllCities(request);
    response.setSuccess(page.getContent(), page.getTotalElements());
    log.info("END: get all cities");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Find city by cityId")
  @GetMapping("/{cityId}")
  public ResponseEntity<GetSingleItemResponse<City>> findCityById(
      @PathVariable(name = "cityId") Integer id) {
    log.info("START: find city by id: " + id);
    GetSingleItemResponse<City> response = new GetSingleItemResponse<>();
    City city = cityService.findCityById(id);
    response.setSuccess(city);
    log.info("END: find city by id");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Get Departure by city id")
  @PostMapping("/{cityId}/departure")
  public ResponseEntity<BaseResponse> getDepartureByCity(
      @PathVariable(name = "cityId") Integer id) {
    log.info("START: get departure in city: " + id);
    GetArrayResponse<Departure> res = new GetArrayResponse<>();
    List<Departure> result = cityService.getDepartureByCity(id);
    res.setSuccess(result, result.size());
    log.info("END: get departure in city:");
    return ResponseEntity.ok(res);
  }

  @ApiOperation(value = "Get Destionation By City Id")
  @PostMapping("/{cityId}/destination")
  public ResponseEntity<BaseResponse> getDestinationByCityId(
      @PathVariable(name = "cityId") Integer id) {
    log.info("[START]: get destination in city: " + id);
    GetArrayResponse<Destination> res = new GetArrayResponse<Destination>();
    List<Destination> results = cityService.getDestinationByCityId(id);
    res.setSuccess(results, results.size());
    log.info("[END]: get destination in city");
    return ResponseEntity.ok(res);
  }
}
