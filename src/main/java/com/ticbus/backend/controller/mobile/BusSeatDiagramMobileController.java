package com.ticbus.backend.controller.mobile;

import com.ticbus.backend.model.BusSeatDiagram;
import com.ticbus.backend.payload.request.BusSeatDiagramListRequest;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.services.BusSeatDiagramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
@RequestMapping("/mobile/diagrams")
@Slf4j
@Api(value = "mobile Bus Seat Diagram Api", description = "mobile Seat Diagram Api")
public class BusSeatDiagramMobileController {

  @Autowired
  private BusSeatDiagramService busSeatDiagramService;


  @ApiOperation(value = "Get all diagram", authorizations = {@Authorization(value = "Bearer")})
  @GetMapping
  public ResponseEntity<GetArrayResponse<BusSeatDiagram>> getAllBuses(
      @Valid BusSeatDiagramListRequest request) {
    log.info("[START]: Get all diagram");
    GetArrayResponse<BusSeatDiagram> response = new GetArrayResponse<>();
    Page<BusSeatDiagram> page = busSeatDiagramService.getAll(request);
    response.setSuccess(page.getContent(), page.getTotalElements());
    log.info("[END]: Get all diagram");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Find diagram By id", authorizations = {@Authorization(value = "Bearer")})
  @PostMapping("/{id}")
  public ResponseEntity<GetSingleItemResponse<BusSeatDiagram>> findBusByLicensePlate(
      @PathVariable(name = "id") Integer id) {
    log.info("[START]: Find diagram By id: " + id);
    GetSingleItemResponse<BusSeatDiagram> response = new GetSingleItemResponse<>();
    BusSeatDiagram busSeatDiagram = busSeatDiagramService.findById(id);
    response.setSuccess(busSeatDiagram);
    log.info("[END]: Find diagram By License Plate");
    return ResponseEntity.ok(response);
  }

}
