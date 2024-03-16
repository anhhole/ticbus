package com.ticbus.backend.payload.request;

import com.ticbus.backend.common.enumeration.EnumStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * @author AnhLH
 */

@Data
@Getter
@Setter
public class DepartureUpdateRequest {

  private String name;
  private String address;
  private EnumStatus status;

}
