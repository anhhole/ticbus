package com.ticbus.backend.payload.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class TripRequest implements Serializable {

  private Integer departureId;

  private Integer destinationId;

  @NotNull
  private String name;


}
