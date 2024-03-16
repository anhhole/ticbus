package com.ticbus.backend.controller.cms;

import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.exception.ResourceNotFoundException;
import com.ticbus.backend.model.Destination;
import com.ticbus.backend.payload.request.DepartureUpdateRequest;
import com.ticbus.backend.payload.request.DestinationRequest;
import com.ticbus.backend.payload.request.PageRequest;
import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.repository.DestinationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.time.Instant;
import java.util.Date;
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
@RequestMapping("/cms/destination")
@Log4j2
@Api(value = "CMS Destination Api", description = "CMS Destination Api")
public class DestinationCMSController {

  @Autowired
  private final DestinationRepository destinationRepository;

  public DestinationCMSController(
      DestinationRepository destinationRepository) {
    this.destinationRepository = destinationRepository;
  }

  @PostMapping("/save")
  public ResponseEntity<BaseResponse> save(@RequestBody DestinationRequest request) {
    BaseResponse res = new BaseResponse();
    if (destinationRepository.existsByAddress(request.getAddress()) || destinationRepository
        .existsByName(request.getName())) {
      res.setDataExist();
      return ResponseEntity.ok(res);
    }
    try {
      log.info("----begin save destination !!");
      Destination destination = new Destination();
      destination.setAddress(request.getAddress());
      destination.setName(request.getName());
      destination.setCreatedTime(Date.from(Instant.now()));
      destination.setStatus(EnumStatus.ENABLE.getId());
      destinationRepository.save(destination);
      res.setSuccess();
      log.info("End save destination ----");
    } catch (Exception e) {
      res.setFailed(e.getMessage());
      log.error("destination Failed Save : " + e.getMessage());
    }
    return ResponseEntity.ok(res);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> update(@PathVariable("id") Integer id,
      @RequestBody DepartureUpdateRequest request) {
    BaseResponse res = new BaseResponse();
    try {
      log.info("----begin update destination !!");
      destinationRepository.findById(id).map(destination -> {
        if (!request.getAddress().isEmpty()) {
          destination.setAddress(request.getAddress());
        }
        if (!request.getName().isEmpty()) {
          destination.setName(request.getName());
        }
        destination.setStatus(request.getStatus().getId());
        return destinationRepository.save(destination);
      });
      res.setSuccess();
      log.info("end update destination !!----");
    } catch (Exception e) {
      res.setFailed(e.getMessage());
      log.error("destination Failed Update destination: " + e.getMessage());
    }
    return ResponseEntity.ok(res);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetSingleItemResponse<Destination>> getOne(@PathVariable("id") Integer id) {
    GetSingleItemResponse<Destination> res = new GetSingleItemResponse<>();
    try {
      log.info("----begin get destination !!");
      Destination destination = destinationRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("destination", "id", id));
      res.setSuccess(destination);
      log.info("end get destination !!----");
    } catch (Exception e) {
      res.setFailed(e.getMessage());
      log.error("destination Failed get destination : " + e.getMessage());
    }
    return ResponseEntity.ok(res);
  }

  @GetMapping
  @ApiOperation(value = "Updating system information", authorizations = {
      @Authorization(value = "Bearer")})
  public ResponseEntity<GetArrayResponse<Destination>> getAll(PageRequest request) {
    GetArrayResponse<Destination> res = new GetArrayResponse<>();
    Page<Destination> page;
    try {
      log.info("----begin get all Destination !!");
      String[] sortSplit = request.getSort().split(",");
      page = destinationRepository.findAll(
          new org.springframework.data.domain.PageRequest(request.getPage(), request.getSize(),
              (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC),
              sortSplit[0]));

      res.setSuccess(page.getContent(), page.getTotalElements());
    } catch (Exception e) {
      log.error("Failed to all Destination : " + e.getMessage());
      res.setServerError();
    }
    log.info("end get all Destination !! ----");
    return ResponseEntity.ok(res);
  }

}
