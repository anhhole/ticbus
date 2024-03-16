package com.ticbus.backend.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class BusListRequest extends PageRequest implements Serializable {

    private String licensePlates;

    private Integer status;

    private Integer numberOfSeat;

    private Integer seatBusType;

    private Integer numberOfFloor;

}
