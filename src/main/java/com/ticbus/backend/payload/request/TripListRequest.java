package com.ticbus.backend.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TripListRequest extends PageRequest{
    private String departureAddress;
    private String name;
    private String destinationAddress;
    private Integer status;
    private String departureName;
    private String destinationName;
}
