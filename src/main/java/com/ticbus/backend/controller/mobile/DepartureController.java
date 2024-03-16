package com.ticbus.backend.controller.mobile;

import com.ticbus.backend.model.Departure;
import com.ticbus.backend.payload.request.SearchRequest;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.repository.DepartureRepository;
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
@RequestMapping("/mobile/departure")
@Log4j2
@Api(value = "Mobile Departure Api", description = "Mobile Departure Api")
public class DepartureController {

  @Autowired
  private final DepartureRepository departureRepository;

  public DepartureController(DepartureRepository departureRepository) {
    this.departureRepository = departureRepository;
  }

  @PostMapping("/search")
  @ApiOperation(value = "Search Departure by address")
  public ResponseEntity<GetArrayResponse<Departure>> search(@RequestBody SearchRequest request) {
    GetArrayResponse<Departure> res = new GetArrayResponse<>();
    List<Departure> list;
    try {
      log.info("----begin search departure !!");
      list = departureRepository.searchByAddress(request.getSearch());
      res.setSuccess(list, list.size());
    } catch (Exception e) {
      log.error("Failed to search departure : " + e.getMessage());
      res.setServerError();
    }
    log.info("end search departure !! ----");
    return ResponseEntity.ok(res);
  }
}
