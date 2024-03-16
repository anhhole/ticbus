//package com.ticbus.backend.services;
//
//import com.ticbus.backend.common.enumeration.EnumStatus;
//import com.ticbus.backend.model.Seat;
//import com.ticbus.backend.payload.request.SeatRequest;
//import com.ticbus.backend.payload.request.SeatUpdateRequest;
//import com.ticbus.backend.payload.response.BaseResponse;
//import com.ticbus.backend.repository.SeatRepository;
//import java.sql.Date;
//import java.time.Instant;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//
///**
// * @author AnhLH
// */
//@Service
//@Log4j2
//public class SeatService {
//
//  @Autowired
//  private final SeatRepository seatRepository;
//
//  public SeatService(SeatRepository seatRepository) {
//    this.seatRepository = seatRepository;
//  }
//
//  public BaseResponse create(SeatRequest request) {
//    BaseResponse response = new BaseResponse();
//    if (seatRepository.existsByRowAndName(request.getRow(), request.getName())) {
//      response.setDataExist();
//      return response;
//    }
//    try {
//      Seat seat = new Seat();
//      seat.setName(request.getName());
//      seat.setRow(request.getRow());
//      seat.setStatus(EnumStatus.ENABLE.getId());
//      seat.setCreatedTime(Date.from(Instant.now()));
//      seat.setPrice(request.getPrice());
//      Seat seatOptional = seatRepository.save(seat);
//      if(!Objects.isNull(seatOptional)){
//        response.setSuccess();
//      }
//    }catch (Exception e){
//      log.error("[Seat] - error when trying to save record : " + e.getMessage());
//      response.setFailed(e.getMessage());
//    }
//    return response;
//  }
//
//  public BaseResponse update(SeatUpdateRequest request){
//    BaseResponse response = new BaseResponse();
//    try{
//      Seat seat = seatRepository.findById(request.getId()).map(seatDb ->{
//         if(Optional.ofNullable(request.getPrice()).orElse(0L) != 0){
//           seatDb.setPrice(request.getPrice());
//         }
//         return seatRepository.save(seatDb);
//      }).orElse(null);
//      if(Objects.isNull(seat)){
//        response.setItemNotFound();
//      }
//    }catch (Exception e){
//      log.error("[Seat] - error when trying to update record : " + e.getMessage());
//      response.setFailed(e.getMessage());
//    }
//    return response;
//  }
//  @Cacheable(value = "seat", key = "#id")
//  public Seat getSeat(Integer id){
//    return seatRepository.findById(id).orElse(null);
//  }
//
//  public List<Seat> getAll(){ return seatRepository.findAll();}
//}
