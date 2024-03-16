package com.ticbus.backend.controller.cms;

import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.model.Departure;
import com.ticbus.backend.payload.request.DepartureRequest;
import com.ticbus.backend.payload.request.DepartureUpdateRequest;
import com.ticbus.backend.payload.request.PageRequest;
import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.repository.DepartureRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.time.Instant;
import java.util.Date;
import javax.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/departures")
@Log4j2
@Api(value = "CMS Departure Api", description = "CMS Departure Api")
public class DepartureCMSController {

  @Autowired
  private final DepartureRepository departureRepository;
  @Autowired
  private final EntityManager entityManager;

  public DepartureCMSController(DepartureRepository departureRepository,
      EntityManager entityManager) {
    this.departureRepository = departureRepository;
    this.entityManager = entityManager;
  }

  @PostMapping()
  @ApiOperation(value = "Updating system information", authorizations = {
      @Authorization(value = "Bearer")})
  public ResponseEntity<BaseResponse> save(@RequestBody DepartureRequest request) {
    BaseResponse res = new BaseResponse();
    if (departureRepository.existsByAddress(request.getAddress()) || departureRepository
        .existsByName(request.getName())) {
      res.setDataExist();
      return ResponseEntity.ok(res);
    }
    try {
      log.info("----begin save departure !!");
      Departure departure = new Departure();
      departure.setAddress(request.getAddress());
      departure.setName(request.getName());
      departure.setCreatedTime(Date.from(Instant.now()));
      departure.setStatus(EnumStatus.ENABLE.getId());
      departureRepository.save(departure);
      res.setSuccess();
      log.info("End save departure ----");
    } catch (Exception e) {
      res.setFailed(e.getMessage());
      log.error("Departure Failed Save : " + e.getMessage());
    }
    return ResponseEntity.ok(res);
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Updating system information", authorizations = {
      @Authorization(value = "Bearer")})
  public ResponseEntity<BaseResponse> update(@PathVariable("id") Integer id,
      @RequestBody DepartureUpdateRequest request) {
    BaseResponse res = new BaseResponse();
    try {
      log.info("----begin update departure !!");
      departureRepository.findById(id).map(departure1 -> {
        if (!request.getAddress().isEmpty()) {
          departure1.setAddress(request.getAddress());
        }
        if (!request.getName().isEmpty()) {
          departure1.setName(request.getName());
        }
        departure1.setStatus(request.getStatus().getId());
        return departureRepository.save(departure1);
      });
      res.setSuccess();
      log.info("end update departure !!----");
    } catch (Exception e) {
      res.setFailed(e.getMessage());
      log.error("Departure Failed Update Departure: " + e.getMessage());
    }
    return ResponseEntity.ok(res);
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Updating system information", authorizations = {
      @Authorization(value = "Bearer")})
  public ResponseEntity<GetSingleItemResponse<Departure>> getOne(@PathVariable("id") Integer id) {
    GetSingleItemResponse<Departure> res = new GetSingleItemResponse<>();
    try {
      log.info("----begin get departure !!");
      Departure departure = departureRepository.findById(id).orElse(new Departure());
      res.setSuccess(departure);

    } catch (Exception e) {
      res.setFailed(e.getMessage());
      log.error("Departure Failed get Departure : " + e.getMessage());
    }
    log.info("end get departure !!----");
    return ResponseEntity.ok(res);
  }

  @GetMapping()
  @ApiOperation(value = "Updating system information", authorizations = {
      @Authorization(value = "Bearer")})
  public ResponseEntity<GetArrayResponse<Departure>> getAll(PageRequest request) {
    GetArrayResponse<Departure> res = new GetArrayResponse<>();
    Page<Departure> page;
    try {
      log.info("----begin get all departure !!");
      String[] sortSplit = request.getSort().split(",");
      page = departureRepository.findAll(
          new org.springframework.data.domain.PageRequest(request.getPage(), request.getSize(),
              (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC),
              sortSplit[0]));

      res.setSuccess(page.getContent(), page.getTotalElements());
    } catch (Exception e) {
      log.error("Failed to search departure : " + e.getMessage());
      res.setServerError();
    }
    log.info("end get all departure !! ----");
    return ResponseEntity.ok(res);
  }

}
