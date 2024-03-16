package com.ticbus.backend.payload.request;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TripSearchRequest implements Serializable {

  private Integer departureId;

  private Integer destinationId;

  private Date departureTime;

  private Integer page;

  private Integer size;

  private String sort;
}
