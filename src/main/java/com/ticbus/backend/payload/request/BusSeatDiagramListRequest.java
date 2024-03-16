package com.ticbus.backend.payload.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author AnhLH
 */

@Getter
@Setter
public class BusSeatDiagramListRequest extends PageRequest {

  private String name;
  private Integer numberOfSeat;
  private Integer seatBusType;
  private Integer status;
  private Integer floor;
  private Integer numberOfRow;
  private Integer numberOfColumn;
}
