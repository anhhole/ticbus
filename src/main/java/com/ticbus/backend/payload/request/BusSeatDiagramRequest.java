package com.ticbus.backend.payload.request;

import com.ticbus.backend.model.SeatDiagramList;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AnhLH
 */
@Data
@Setter
@Getter
public class BusSeatDiagramRequest {

  @NotNull
  private String name;
  @NotNull
  private Integer numberOfSeat;
  @NotNull
  private Integer seatBusType;
  @NotNull
  private Integer floor;
  @NotNull
  private SeatDiagramList seatDiagram;
  @NotNull
  private Integer numberOfRow;
  @NotNull
  private Integer numberOfColumn;
}
