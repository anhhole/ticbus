package com.ticbus.backend.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
public class SchedulerRequest {
    private Integer tripId;
    private Integer busId;
    private Date startTime;
    private Date endTime;
}
