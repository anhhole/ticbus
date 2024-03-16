package com.ticbus.backend.services;

import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.exception.EntityType;
import com.ticbus.backend.exception.ExceptionType;
import com.ticbus.backend.model.Trip;
import com.ticbus.backend.model.specification.TripSpecification;
import com.ticbus.backend.payload.request.TripListRequest;
import com.ticbus.backend.payload.request.TripRequest;
import com.ticbus.backend.payload.request.TripUpdateRequest;
import com.ticbus.backend.repository.DepartureRepository;
import com.ticbus.backend.repository.DestinationRepository;
import com.ticbus.backend.repository.TripRepository;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TripService extends BaseService {

  @Autowired
  private TripRepository tripRepository;

  @Autowired
  private DepartureRepository departureRepository;

  @Autowired
  private DestinationRepository destinationRepository;

  @Autowired
  private TripSpecification tripSpecification;

  public Trip createNewTrip(TripRequest request) {
    try {
      if (!Objects.isNull(request)) {
//                Optional<Trip> optTrip = tripRepository.findByDepartureIdAndDestinationId(request.getDepartureId(),
//                        request.getDestinationId());
//                if (optTrip.isPresent()) {
//                    throw exception(EntityType.TRIP,ExceptionType.ENTITY_EXCEPTION,request.toString());
//                }
        if (tripRepository.existsByName(request.getName())) {
          throw exception(EntityType.TRIP, ExceptionType.DUPLICATE_ENTITY, request.getName());
        }
        Trip trip = new Trip();
        trip.setCreatedTime(Date.from(Instant.now()));
        trip.setDeparture(departureRepository.findById(request.getDepartureId()).orElseThrow(() ->
            exception(EntityType.DEPARTURE, ExceptionType.ENTITY_NOT_FOUND,
                String.valueOf(request.getDepartureId()))));
        trip.setDestination(
            destinationRepository.findById(request.getDestinationId()).orElseThrow(() ->
                exception(EntityType.DESTINATION, ExceptionType.ENTITY_NOT_FOUND,
                    String.valueOf(request.getDestinationId()))));
        trip.setStatus(EnumStatus.ENABLE.getId());
        trip.setName(request.getName());
        return tripRepository.save(trip);
      }
    } catch (Exception e) {
      log.error("Exception while creating new trip: ", e.getMessage());
    }
    throw exception(EntityType.TRIP, ExceptionType.ENTITY_EXCEPTION, request.toString());
  }

  public Page<Trip> getAllTripInformation(TripListRequest pageRequest) {
    String[] sortSplit = pageRequest.getSort().split(",");
    if (!Objects.isNull(pageRequest.getSearch())) {
      return tripRepository
          .findAll(TripSpecification.textInAllColumns(pageRequest.getSearch()),
              new org.springframework.data.domain.PageRequest(pageRequest.getPage(),
                  pageRequest.getSize(),
                  (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                      : Sort.Direction.DESC), sortSplit[0]));
    } else {
      return tripRepository
          .findAll(tripSpecification.getFilter(pageRequest),
              new org.springframework.data.domain.PageRequest(pageRequest.getPage(),
                  pageRequest.getSize(),
                  (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                      : Sort.Direction.DESC), sortSplit[0]));
    }
  }


  public Trip updateTripById(TripUpdateRequest request) {
    return tripRepository.findTripById(request.getId()).map(trip -> {
      if (StringUtils.isNotEmpty(request.getName())) {
        trip.setName(request.getName());
      }
      if (request.getDepartureId() != null && request.getDepartureId() > 0) {
        trip.setDeparture(departureRepository.findById(request.getDepartureId()).orElseThrow(() ->
            exception(EntityType.DEPARTURE, ExceptionType.ENTITY_NOT_FOUND,
                String.valueOf(request.getDepartureId()))));
      }
      if (request.getDestinationId() != null && request.getDestinationId() > 0) {
        trip.setDestination(
            destinationRepository.findById(request.getDestinationId()).orElseThrow(() ->
                exception(EntityType.DESTINATION, ExceptionType.ENTITY_NOT_FOUND,
                    String.valueOf(request.getDestinationId()))));
      }
      trip.setLastModifiedDate(Date.from(Instant.now()));
      if (tripRepository
          .findByDepartureIdAndDestinationId(request.getDepartureId(), request.getDestinationId())
          .isPresent()) {
        throw exception(EntityType.TRIP, ExceptionType.DUPLICATE_ENTITY, request.toString());

      }
      return tripRepository.save(trip);
    }).orElseThrow(() -> exception(EntityType.TRIP, ExceptionType.ENTITY_NOT_FOUND,
        String.valueOf(request.getId())));
  }


  public Trip changeStatus(Integer id, EnumStatus status) {
    return tripRepository.findTripById(id).map(trip -> {
      trip.setStatus(status.getId());
      return tripRepository.save(trip);
    }).orElseThrow(
        () -> exception(EntityType.TRIP, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(id)));
  }


  public Trip findTripById(Integer id) {
    return tripRepository.findTripById(id)
        .orElseThrow(() -> exception(EntityType.TRIP, ExceptionType.ENTITY_NOT_FOUND,
            String.valueOf(id)));
  }

}
