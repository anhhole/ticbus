package com.ticbus.backend.services;

import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.exception.EntityType;
import com.ticbus.backend.exception.ExceptionType;
import com.ticbus.backend.exception.TicbusException;
import com.ticbus.backend.model.BusSeatDiagram;
import com.ticbus.backend.model.specification.BusSeatDiagramSpecification;
import com.ticbus.backend.payload.request.BusSeatDiagramListRequest;
import com.ticbus.backend.payload.request.BusSeatDiagramRequest;
import com.ticbus.backend.repository.BusSeatDiagramRepository;
import java.sql.Date;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author AnhLH
 */
@Service
public class BusSeatDiagramService {

  @Autowired
  private final BusSeatDiagramRepository busSeatDiagramRepository;

  @Autowired
  private final BusSeatDiagramSpecification busSeatDiagramSpecification;

  public BusSeatDiagramService(
      BusSeatDiagramRepository busSeatDiagramRepository,
      BusSeatDiagramSpecification busSeatDiagramSpecification) {
    this.busSeatDiagramRepository = busSeatDiagramRepository;
    this.busSeatDiagramSpecification = busSeatDiagramSpecification;
  }

  public BusSeatDiagram save(BusSeatDiagramRequest request) {
    Optional<BusSeatDiagram> busSeatDiagramOptional = busSeatDiagramRepository
        .findByName(request.getName());
    if (busSeatDiagramOptional.isPresent()) {
      throw exception(EntityType.BUS_SEAT_DIAGRAM, ExceptionType.ENTITY_NOT_FOUND,
          request.getName());
    }
    BusSeatDiagram busSeatDiagram = new BusSeatDiagram();
    busSeatDiagram.setName(request.getName());
    busSeatDiagram.setFloor(request.getFloor());
    busSeatDiagram.setNumberOfSeat(request.getNumberOfSeat());
    busSeatDiagram.setSeatDiagram(request.getSeatDiagram());
    busSeatDiagram.setStatus(EnumStatus.ENABLE.getId());
    busSeatDiagram.setCreatedTime(Date.from(Instant.now()));
    busSeatDiagram.setSeatBusType(request.getSeatBusType());
    busSeatDiagram.setNumberOfColumn(request.getNumberOfColumn());
    busSeatDiagram.setNumberOfRow(request.getNumberOfRow());
    return busSeatDiagramRepository.save(busSeatDiagram);
  }


  @Cacheable(value = "bus_seat_diagram", key = "#id", unless = "#result != null")
  public BusSeatDiagram findById(int id) {
    return busSeatDiagramRepository.findById(id).orElseThrow(
        () -> exception(EntityType.BUS_SEAT_DIAGRAM, ExceptionType.ENTITY_NOT_FOUND,
            String.valueOf(id)));
  }

  public Page<BusSeatDiagram> getAll(BusSeatDiagramListRequest pageRequest) {
    String[] sortSplit = pageRequest.getSort().split(",");
    if (!Objects.isNull(pageRequest.getSearch())) {
      return busSeatDiagramRepository
          .findAll(BusSeatDiagramSpecification.textInAllColumns(pageRequest.getSearch()),
              new org.springframework.data.domain.PageRequest(pageRequest.getPage(),
                  pageRequest.getSize(),
                  (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                      : Sort.Direction.DESC), sortSplit[0]));
    } else {
      return busSeatDiagramRepository
          .findAll(busSeatDiagramSpecification.getFilter(pageRequest),
              new org.springframework.data.domain.PageRequest(pageRequest.getPage(),
                  pageRequest.getSize(),
                  (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                      : Sort.Direction.DESC), sortSplit[0]));
    }
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
}
