package com.ticbus.backend.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SystemInformationRequest {


  private String busStationName;

  /**
   * slogan: optional
   */
  private String slogan;

  private String address1;

  private String address2;

  private String description;

  private String busStationRouteDescription;

  private String phone;

  /**
   * websiteName: url
   */
  private String websiteName;

  private String hotlineNumber;
}
