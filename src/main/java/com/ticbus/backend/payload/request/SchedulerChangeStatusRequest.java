package com.ticbus.backend.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SchedulerChangeStatusRequest {

    @NotNull
    private Integer status;
}
