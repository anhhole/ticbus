//package com.ticbus.backend.controller.cms;
//
//import com.ticbus.backend.model.Seat;
//import com.ticbus.backend.payload.request.SeatRequest;
//import com.ticbus.backend.payload.response.BaseResponse;
//import com.ticbus.backend.payload.response.GetArrayResponse;
//import com.ticbus.backend.payload.response.GetSingleItemResponse;
//
//import io.swagger.annotations.Api;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.Link;
//import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.Resources;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
///**
// * @author AnhLH
// */
//@RestController
//@RequestMapping("/cms/seats")
//@Log4j2
//@Api(value = "CMS Departure Api", description = "CMS Departure Api")
//public class SeatController {
//  @Autowired
//  private  final SeatService seatService;
//
//  public SeatController(SeatService seatService) {
//    this.seatService = seatService;
//  }
//
//  @PostMapping
//  public ResponseEntity<BaseResponse> create(@RequestBody SeatRequest request){
//    log.info("----[Begin] create new seat ");
//    BaseResponse res = seatService.create(request);
//    log.info("[END] created new seat ---");
//
//    return ResponseEntity.status(HttpStatus.CREATED).body(res);
//  }
//  @GetMapping("/{id}")
//  public ResponseEntity<GetSingleItemResponse<SeatResource>> getOne(@PathVariable Integer id){
//    log.info("----[Begin] get seat : " + id);
//    GetSingleItemResponse<SeatResource> res = new GetSingleItemResponse<>();
//    Seat seat = seatService.getSeat(id);
//    log.info("[END] get seat ---");
//    res.setSuccess(new SeatResource(seat));
//    return ResponseEntity.status(HttpStatus.OK).body(res);
//  }
//  @GetMapping
//  public ResponseEntity<GetArrayResponse<Resources<SeatResource>>> all(){
//    GetArrayResponse<Resources<SeatResource>> res = new GetArrayResponse<>();
//    List<SeatResource> seatList = seatService.getAll().stream().map(SeatResource::new).collect(Collectors.toList());
//    Resources<SeatResource> resource = new Resources<>(seatList);
//    String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
//    resource.add(new Link(uriString,"self"));
//    res.setSuccess(Collections.singletonList(resource),resource.getContent().size());
//    return ResponseEntity.ok(res);
//  }
//}
