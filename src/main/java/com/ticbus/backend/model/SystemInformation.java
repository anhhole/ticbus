package com.ticbus.backend.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_system")
public class SystemInformation extends BaseModel implements Serializable {

  private static final long serialVersionUID = 7156526077883281625L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "bus_station_name")
  private String busStationName;

  private String address1;

  private String address2;

  private String description;

  @Column(name = "bus_station_route_description")
  private String busStationRouteDescription;

  private String phone;

  /**
   * websiteName: url
   */
  @Column(name = "website_name")
  private String websiteName;
  @Column(name = "hotline_number")
  private String hotlineNumber;


}
