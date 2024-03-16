package com.ticbus.backend.model;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AnhLH
 */
@Data
@Getter
@Setter
public class SeatDiagram implements Serializable {

  private Integer row;
  private String column;
  private Integer isHadSeat;
  private Integer status; // 0: driver ,1 : booked, 2 : available , 3 : paid, 4: waitToPay
  private Integer floor;
}
