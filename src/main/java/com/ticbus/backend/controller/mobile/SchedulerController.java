package com.ticbus.backend.controller.mobile;

import com.ticbus.backend.model.Scheduler;
import com.ticbus.backend.payload.response.GetSingleItemResponse;
import com.ticbus.backend.services.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile")
@Log4j2
@Api(value = "Mobile schedulers Api", description = "Mobile schedulers Api")
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @ApiOperation(value = "Find Schedule By Id", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/schedulers/{schedulerId}")
    public ResponseEntity<GetSingleItemResponse<Scheduler>> findScheduleById(@PathVariable(value = "schedulerId")
                                                                                     Integer schedulerId){
        log.info("[START]: find scheduler by id: " + schedulerId);
        GetSingleItemResponse<Scheduler> response = new GetSingleItemResponse<Scheduler>();
        Scheduler scheduler = schedulerService.findScheduleById(schedulerId);
        response.setSuccess(scheduler);
        log.info("[END]: find scheduler by id");
        return ResponseEntity.ok(response);
    }
}
