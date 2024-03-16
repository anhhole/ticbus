package com.ticbus.backend.controller.mobile;

import com.ticbus.backend.model.Destination;
import com.ticbus.backend.model.TicketJson;
import com.ticbus.backend.payload.request.PageRequest;
import com.ticbus.backend.payload.response.GetArrayResponse;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile/ticket")
@Log4j2
@Api(value = "Mobile Ticket Api", description = "Mobile Ticket Api")
public class TicketController {


  @PostMapping("/all")
  public ResponseEntity<GetArrayResponse<TicketJson>> getAll(@RequestBody PageRequest request) {
    GetArrayResponse<TicketJson> res = new GetArrayResponse<>();
    Page<Destination> page;
    try {
      String[] sortSplit = request.getSort().split(",");
      List<TicketJson> list = new ArrayList<>();
      TicketJson json = new TicketJson();
      json.setId(1);
      json.setDeparture("Hà Nội");
      json.setDestination("Tp Hồ Chí Minh");
      json.setPrice((long) 2000000);
      json.setStartTime("30/08/2019 14:00:00");
      json.setEndTime("03/09/2019 18:00:00");
      json.setSeatLeft(1);
      json.setBus("Xe 16 cho");
      TicketJson json1 = new TicketJson();
      json1.setId(2);
      json1.setDeparture("Đà Nẵng");
      json1.setDestination("Tp Hồ Chí Minh");
      json1.setPrice((long) 1000000);
      json1.setStartTime("30/08/2019 11:00:00");
      json1.setEndTime("01/09/2019 17:00:00");
      json1.setSeatLeft(10);
      json1.setBus("Xe 32 cho");
      TicketJson json2 = new TicketJson();
      json2.setId(3);
      json2.setDeparture("Huế");
      json2.setDestination("Quảng Ninh");
      json2.setPrice((long) 1000000);
      json2.setStartTime("28/08/2019 10:00:00");
      json2.setEndTime("29/08/2019 19:00:00");
      json2.setSeatLeft(12);
      json2.setBus("limouse 16 cho");
      list.add(json);
      list.add(json1);
      list.add(json2);

      res.setSuccess(list, list.size());
    } catch (Exception e) {
      res.setServerError();
    }
    return ResponseEntity.ok(res);
  }
}
