//package com.ticbus.backend.payload.response;
//
//import com.ticbus.backend.controller.cms.SeatController;
//import com.ticbus.backend.model.Seat;
//import lombok.Getter;
//import org.springframework.hateoas.ResourceSupport;
//import org.springframework.hateoas.mvc.ControllerLinkBuilder;
//
///**
// * @author AnhLH
// */
//@Getter
//public class SeatResource extends ResourceSupport {
//  private final Seat seat;
//  public SeatResource(final Seat seat){
//    this.seat = seat;
//    int id = seat.getId();
//    add(ControllerLinkBuilder.linkTo(SeatController.class).withRel("seat"));
//    add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SeatController.class).getOne(id)).withSelfRel());
//  }
//}
