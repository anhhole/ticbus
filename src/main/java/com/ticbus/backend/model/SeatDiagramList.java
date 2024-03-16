package com.ticbus.backend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SeatDiagramList implements Serializable {

  private List<SeatDiagram> seatDiagramList = new ArrayList<>();
}
