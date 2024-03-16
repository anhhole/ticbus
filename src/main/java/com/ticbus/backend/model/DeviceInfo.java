package com.ticbus.backend.model;

import com.ticbus.backend.common.enumeration.DeviceType;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
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
public class DeviceInfo {

  @NotBlank(message = "Device id cannot be blank")
  @ApiModelProperty(value = "Device Id", required = true, dataType = "string", allowableValues = "Non empty string")
  private String deviceId;

  @NotNull(message = "Device type cannot be null")
  @ApiModelProperty(value = "Device type Android/iOS/web", required = true, dataType = "string", allowableValues =
      "ANDROID,IOS,WEB")
  private DeviceType deviceType;
}
