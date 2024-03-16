package com.ticbus.backend.services;

import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.exception.EntityType;
import com.ticbus.backend.exception.ExceptionType;
import com.ticbus.backend.exception.TicbusException;
import com.ticbus.backend.model.Bus;
import com.ticbus.backend.model.specification.BusSpecification;
import com.ticbus.backend.payload.request.BusListRequest;
import com.ticbus.backend.payload.request.BusRequest;
import com.ticbus.backend.payload.request.BusUpdateRequest;
import com.ticbus.backend.repository.BusRepository;
import com.ticbus.backend.repository.BusSeatDiagramRepository;
import java.time.Instant;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class BusService {

  @Autowired
  final private BusRepository busRepository;

    @Autowired
    private final  BusSpecification busSpecification;
    @Autowired
    private BusSeatDiagramRepository busSeatDiagramRepository;

    public BusService(BusRepository busRepository, BusSpecification busSpecification) {
        this.busRepository = busRepository;
        this.busSpecification = busSpecification;
    }

  /**
   * Create new bus
   *
   * @return Bus Object
   */
  public Bus createNewBus(BusRequest request) {
    if (StringUtils.isNotEmpty(request.getLicensePlates())) {
      if (checkBusSeatDiagramExisted(request.getBusSeatDiagramId())) {
        if (!checkLicensePlateExisted(request.getLicensePlates())) {
          Bus bus = new Bus();
          bus.setLicensePlates(request.getLicensePlates());
          bus.setCreatedTime(Date.from(Instant.now()));
          bus.setStatus(EnumStatus.ENABLE.getId());
          bus.setSeatBusDiagram(
              busSeatDiagramRepository.findById(request.getBusSeatDiagramId()).get());
          return busRepository.save(bus);
        }
      }
      throw exception(EntityType.BUS, ExceptionType.DUPLICATE_ENTITY, request.getLicensePlates());
    }
    throw exception(EntityType.BUS, ExceptionType.ENTITY_EXCEPTION, request.getLicensePlates());
  }

  /**
   * Update Bus
   *
   * @return BusResponse
   */
//  @CachePut(value = "bus", key = "#request.id")
  public Bus updateBus(BusUpdateRequest request) {
    return busRepository.findBusByLicensePlates(request.getLicensePlates()).map(bus -> {
      if (checkBusSeatDiagramExisted(request.getBusSeatDiagramId())) {
        bus.setLastModifiedDate(Date.from(Instant.now()));
        bus.setSeatBusDiagram(
            busSeatDiagramRepository.findById(request.getBusSeatDiagramId()).get());
      }
      return busRepository.save(bus);
    }).orElseThrow(() -> exception(EntityType.BUS, ExceptionType.ENTITY_NOT_FOUND,
        request.getLicensePlates()));
  }

  private boolean checkLicensePlateExisted(String licensePlates) {
    boolean isExisted = false;
    Optional<Bus> bus = busRepository.findBusByLicensePlates(licensePlates);
    if (bus.isPresent()) {
      isExisted = true;
    }
    return isExisted;
  }

  private boolean checkBusSeatDiagramExisted(Integer id) {
    boolean isExisted = false;
    if (busSeatDiagramRepository.findById(id).isPresent()) {
      isExisted = true;
    }
    return isExisted;
  }

    public Page<Bus> getAllBuses(BusListRequest request) {
        String[] sortSplit = request.getSort().split(",");
        if (!Objects.isNull(request.getSearch())) {
            return busRepository
                    .findAll(BusSpecification.textInAllColumns(request.getSearch()),
                            new org.springframework.data.domain.PageRequest(request.getPage(),
                                    request.getSize(),
                                    (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                                            : Sort.Direction.DESC), sortSplit[0]));
        } else {
            return busRepository
                    .findAll(busSpecification.getFilter(request),
                            new org.springframework.data.domain.PageRequest(request.getPage(),
                                    request.getSize(),
                                    (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                                            : Sort.Direction.DESC), sortSplit[0]));
        }
    }

  @Cacheable(value = "bus", key = "#licensePlate")
  public Bus findBusByLicensePlate(String licensePlate) {
    return busRepository.findBusByLicensePlates(licensePlate)
        .orElseThrow(() -> exception(EntityType.BUS, ExceptionType.ENTITY_NOT_FOUND,
            licensePlate));
  }


  /**
   * Returns a new RuntimeException
   */
  private RuntimeException exception(EntityType entityType, ExceptionType exceptionType,
      String... args) {
    return TicbusException.throwException(entityType, exceptionType, args);
  }

  /**
   * Returns a new RuntimeException
   */
  private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType,
      String id, String... args) {
    return TicbusException.throwExceptionWithId(entityType, exceptionType, id, args);
  }

//    private List<Seat> generateSeats(Bus bus) {
//        List<Seat> listSeat = new ArrayList<Seat>();
//        EnumSeatBusType seatBusType = EnumSeatBusType.(bus.getSeatBusType());
//        int seats = 0;
//        if (seatBusType.equals(EnumSeatBusType.SEAT_16)) {
//            seats = SIXTEEN_SEATS;
//        } else if (seatBusType.equals(EnumSeatBusType.SEAT_32)) {
//            seats = THIRTY_TWO_SEATS;
//        }
//        Seat seat = null;
//        for (int i = 0; i < seats; i++) {
//            for (int j = 0; j < seatAlphabet.length; j++) {
//                seat = new Seat();
////                seat.setConfirm(Boolean.FALSE);
////                seat.setLocation(seatAlphabet[j] + "-" + (i + 1));
//                listSeat.add(seat);
//            }
//
//        }
//        return listSeat;
//    }
}
