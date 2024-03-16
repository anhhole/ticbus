package com.ticbus.backend.payload.request;

import com.ticbus.backend.model.DeviceInfo;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AnhLH
 */
@Data
@Getter
@Setter
public class LogOutRequest {

  @NotNull
  private DeviceInfo deviceInfo;
}
