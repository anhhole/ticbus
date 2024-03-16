package com.ticbus.backend.payload.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SchedulerUpdateRequest {
    private Integer id;
    private Integer tripId;
    private Integer busId;
    private Date startTime;
    private Date endTime;
}
