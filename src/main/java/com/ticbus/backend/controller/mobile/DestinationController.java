package com.ticbus.backend.controller.mobile;

import com.ticbus.backend.model.Destination;
import com.ticbus.backend.payload.request.SearchRequest;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.repository.DestinationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile/destination")
@Log4j2
@Api(value = "Mobile Destination Api", description = "Mobile Destination Api")
public class DestinationController {

  @Autowired
  private final DestinationRepository destinationRepository;

  public DestinationController(DestinationRepository destinationRepository) {
    this.destinationRepository = destinationRepository;
  }

  @PostMapping("/search")
  @ApiOperation(value = "Search destination by address")
  public ResponseEntity<GetArrayResponse<Destination>> search(@RequestBody SearchRequest request) {
    GetArrayResponse<Destination> res = new GetArrayResponse<>();
    List<Destination> list;
    try {
      log.info("----begin search destination !!");
      list = destinationRepository.searchByAddress(request.getSearch());
      res.setSuccess(list, list.size());
    } catch (Exception e) {
      log.error("Failed to search destination : " + e.getMessage());
    }
    log.info("end search destination !! ----");
    return ResponseEntity.ok(res);
  }
}
