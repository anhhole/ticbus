package com.ticbus.backend.payload.request;

import com.ticbus.backend.common.enumeration.EnumStatus;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CityChangeStatusRequest {

  @NotNull
  private EnumStatus status;
}
