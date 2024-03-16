package com.ticbus.backend.payload.request;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TripChangeStatusRequest {

  @NotNull
  private Integer status;
}
