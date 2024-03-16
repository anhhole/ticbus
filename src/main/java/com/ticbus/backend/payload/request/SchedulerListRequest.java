package com.ticbus.backend.payload.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SchedulerListRequest extends PageRequest{

    private String tripName;

    private Integer status;


}
