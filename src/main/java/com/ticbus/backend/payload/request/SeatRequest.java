package com.ticbus.backend.payload.request;

import com.ticbus.backend.common.enumeration.EnumKindSeat;
import com.ticbus.backend.common.enumeration.EnumStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AnhLH
 */
@Getter
@Setter
@Data
public class SeatRequest {

  private Integer row;
  private String name;
  private Long price;
  private EnumKindSeat kind;
  private EnumStatus status;
}
