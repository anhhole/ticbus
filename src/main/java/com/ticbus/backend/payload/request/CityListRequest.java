package com.ticbus.backend.payload.request;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CityListRequest extends PageRequest implements Serializable {

  private String city;

  private String province;

  private Integer status;
}
