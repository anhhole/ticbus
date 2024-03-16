package com.ticbus.backend.controller.cms;

import com.ticbus.backend.model.Scheduler;
import com.ticbus.backend.payload.request.SchedulerChangeStatusRequest;
import com.ticbus.backend.payload.request.SchedulerListRequest;
import com.ticbus.backend.payload.request.SchedulerRequest;
import com.ticbus.backend.payload.request.SchedulerUpdateRequest;
import com.ticbus.backend.payload.response.BaseResponse;
import com.ticbus.backend.payload.response.GetArrayResponse;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.services.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms")
@Slf4j
@Api(value = "CMS Scheduler Api", description = "CMS Scheduler Api")
public class SchedulerCMSController {

  @Autowired
  private SchedulerService schedulerService;

  @ApiOperation(value = "Create new schedule ", authorizations = {@Authorization(value = "Bearer")})
  @PostMapping("/schedulers")
  public ResponseEntity<BaseResponse> createNewSchedule(@RequestBody SchedulerRequest request) {
    log.info("[START]: create new schedule");
    BaseResponse response = new BaseResponse();
    response.setFailed();
    Optional<Scheduler> optScheduler = Optional
        .ofNullable(schedulerService.createNewSchedule(request));
    if (optScheduler.isPresent()) {
      response.setSuccess();
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    log.info("[END]: create new schedule");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ApiOperation(value = "Update Schedule", authorizations = {@Authorization(value = "Bearer")})
  @PutMapping("/schedulers")
  public ResponseEntity<BaseResponse> updateSchedule(@RequestBody SchedulerUpdateRequest request) {
    log.info("[START]: update schedule");
    BaseResponse response = new BaseResponse();
    response.setFailed();
    Optional<Scheduler> optScheduler = Optional
        .ofNullable(schedulerService.updateSchedule(request));
    if (optScheduler.isPresent()) {
      response.setSuccess();
    }
    log.info("[END]: update schedule");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Get All Schedulers", authorizations = {@Authorization(value = "Bearer")})
  @GetMapping("/schedulers")
  public ResponseEntity<GetArrayResponse<Scheduler>> getAllScheduler(
      @Valid SchedulerListRequest request) {
    log.info("START: get all employees");
    GetArrayResponse<Scheduler> response = new GetArrayResponse<>();
    Page<Scheduler> page = schedulerService.getAllScheduler(request);
    response.setSuccess(page.getContent(), page.getTotalElements());
    log.info("END: get all employees");
    return ResponseEntity.ok(response);
  }


  @ApiOperation(value = "Find Schedule By Id", authorizations = {@Authorization(value = "Bearer")})
  @GetMapping("/schedulers/{schedulerId}")
  public ResponseEntity<GetSingleItemResponse<Scheduler>> findScheduleById(
      @PathVariable(value = "schedulerId")
          Integer schedulerId) {
    log.info("[START]: find scheduler by id: " + schedulerId);
    GetSingleItemResponse<Scheduler> response = new GetSingleItemResponse<Scheduler>();
    Scheduler scheduler = schedulerService.findScheduleById(schedulerId);
    response.setSuccess(scheduler);
    log.info("[END]: find scheduler by id");
    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Change Schedule status", authorizations = {
      @Authorization(value = "Bearer")})
  @PostMapping("/schedulers/{schedulerId}")
  public ResponseEntity<BaseResponse> changeSchedulerById(
      @PathVariable(value = "schedulerId") Integer schedulerId,
      SchedulerChangeStatusRequest request) {
    log.info("[START]: change schedule " + schedulerId + " status");
    BaseResponse response = new BaseResponse();
    response.setFailed();
    Optional<Scheduler> optScheduler = Optional
        .ofNullable(schedulerService.changeSchedulerById(schedulerId, request));
    if (optScheduler.isPresent()) {
      response.setSuccess();
    }
    log.info("[END]: change schedule status");
    return ResponseEntity.ok(response);
  }


}
