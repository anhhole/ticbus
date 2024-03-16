package com.ticbus.backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class TicketJson {

  private Integer id;
  private String destination;
  private String departure;
  private Long price;
  private Integer seatLeft;
  private String bus;
  private String startTime;
  private String endTime;

}
