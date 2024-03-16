package com.ticbus.backend.services;

import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.exception.EntityType;
import com.ticbus.backend.exception.ExceptionType;
import com.ticbus.backend.model.Scheduler;
import com.ticbus.backend.model.Ticket;
import com.ticbus.backend.model.specification.SchedulerSpecification;
import com.ticbus.backend.payload.request.SchedulerChangeStatusRequest;
import com.ticbus.backend.payload.request.SchedulerListRequest;
import com.ticbus.backend.payload.request.SchedulerRequest;
import com.ticbus.backend.payload.request.SchedulerUpdateRequest;
import com.ticbus.backend.repository.BusRepository;
import com.ticbus.backend.repository.SchedulerRepository;
import com.ticbus.backend.repository.TripRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SchedulerService extends BaseService {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private SchedulerSpecification schedulerSpecification;


    public Scheduler createNewSchedule(SchedulerRequest request) {
        if(!Objects.isNull(request)){
            if (schedulerRepository.findByBusAndRangeTime(request.getBusId(), request.getEndTime(), request.getStartTime()).size() > 0) {
                throw exception(EntityType.SCHEDULER, ExceptionType.OVERLAP_TIME_EXCEPTION, String.valueOf(request.getBusId()));
            }
            Scheduler scheduler = new Scheduler();
            scheduler.setBus(busRepository.findBusById(request.getBusId())
                    .orElseThrow(() -> exception(EntityType.BUS, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(request.getBusId()))));
            scheduler.setTrip(tripRepository.findTripById(request.getTripId())
                    .orElseThrow(() -> exception(EntityType.TRIP, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(request.getTripId()))));
            scheduler.setStartTime(request.getStartTime());
            scheduler.setEndTime(request.getEndTime());
            scheduler.setStatus(EnumStatus.ENABLE.getId());
            scheduler.setCreatedTime(Date.from(Instant.now()));
            return schedulerRepository.save(scheduler);
        }
        throw exception(EntityType.SCHEDULER, ExceptionType.ENTITY_EXCEPTION, request.toString());
    }

    public Scheduler updateSchedule(SchedulerUpdateRequest request){
        return schedulerRepository.findSchedulerById(request.getId()).map(scheduler -> {
            if(schedulerRepository.existsByBusIdAndTripIdAndStartTime(request.getBusId(), request.getTripId(),
                    request.getStartTime())){
                throw exception(EntityType.SCHEDULER, ExceptionType.DUPLICATE_ENTITY, request.toString());
            }
            if(schedulerRepository.findByBusAndRangeTime(request.getBusId(), request.getEndTime(), request.getStartTime()).size() > 0){
                throw exception(EntityType.SCHEDULER, ExceptionType.OVERLAP_TIME_EXCEPTION, String.valueOf(request.getBusId()));
            }
            if(request.getBusId() != null && request.getBusId() > 0){
                scheduler.setBus(busRepository.findBusById(request.getBusId()).orElseThrow(() ->
                        exception(EntityType.BUS, ExceptionType.ENTITY_NOT_FOUND,
                                String.valueOf(request.getBusId()))));
            }
            if(request.getTripId() != null && request.getTripId() > 0){
                scheduler.setTrip(tripRepository.findTripById(request.getTripId())
                        .orElseThrow(() -> exception(EntityType.TRIP, ExceptionType.ENTITY_NOT_FOUND,
                                String.valueOf(request.getTripId()))));
            }
            if(!Objects.isNull(request.getStartTime())){
                scheduler.setStartTime(request.getStartTime());
            }
            scheduler.setLastModifiedDate(Date.from(Instant.now()));
            return schedulerRepository.save(scheduler);
        }).orElseThrow(() -> exception(EntityType.SCHEDULER, ExceptionType.ENTITY_NOT_FOUND,
                String.valueOf(request.getId())));
    }

    public Scheduler findScheduleById(Integer id){
        return schedulerRepository.findSchedulerById(id).orElseThrow(() ->
                exception(EntityType.SCHEDULER, ExceptionType.ENTITY_NOT_FOUND,
                String.valueOf(id)));
    }

    public Scheduler changeSchedulerById(Integer schedulerId, SchedulerChangeStatusRequest request){
        return schedulerRepository.findSchedulerById(schedulerId).map(scheduler -> {
            if(!Objects.isNull(request)){
                scheduler.setStatus(request.getStatus());
                scheduler.setLastModifiedDate(Date.from(Instant.now()));
            }
          return schedulerRepository.save(scheduler);
        }).orElseThrow(() ->
                exception(EntityType.SCHEDULER, ExceptionType.ENTITY_NOT_FOUND,
                        String.valueOf(schedulerId)));
    }

    public Page<Scheduler> getAllScheduler(SchedulerListRequest request){
        String[] sortSplit = request.getSort().split(",");
        if (!Objects.isNull(request.getSearch())) {
            return schedulerRepository
                    .findAll(SchedulerSpecification.textInAllColumns(request.getSearch()),
                            new org.springframework.data.domain.PageRequest(request.getPage(),
                                    request.getSize(),
                                    (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                                            : Sort.Direction.DESC), sortSplit[0]));
        } else {
            return schedulerRepository
                    .findAll(schedulerSpecification.getFilter(request),
                            new org.springframework.data.domain.PageRequest(request.getPage(),
                                    request.getSize(),
                                    (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                                            : Sort.Direction.DESC), sortSplit[0]));
        }
    }

    private List<Ticket> generateTicket(){
    return null;
    }

    //Use to check overlap time
    private static boolean isWithinRange(Date start1, Date end1, Date start2, Date end2){
        return start1.before(end2) && start2.before(end1);
    }
}
