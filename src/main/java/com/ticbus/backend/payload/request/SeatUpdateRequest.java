package com.ticbus.backend.payload.request;

import com.ticbus.backend.common.enumeration.EnumKindSeat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AnhLH
 */
@Getter
@Setter
@Data
public class SeatUpdateRequest {

  private Integer id;
  private Long price;
  private EnumKindSeat kind;
}
