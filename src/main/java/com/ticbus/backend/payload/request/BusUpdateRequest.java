package com.ticbus.backend.payload.request;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BusUpdateRequest implements Serializable {

  private String licensePlates;

  private Integer busSeatDiagramId;
}
