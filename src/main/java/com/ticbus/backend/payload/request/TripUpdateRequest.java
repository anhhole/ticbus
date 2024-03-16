package com.ticbus.backend.payload.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TripUpdateRequest implements Serializable {

  private Integer id;

  private Integer departureId;

  private Integer destinationId;

  private String name;

}
