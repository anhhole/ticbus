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
import org.hibernate.annotations.Type;

/**
 * @author AnhLH
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_bus_seat_diagram")
public class BusSeatDiagram extends BaseModel implements Serializable {

  private static final long serialVersionUID = 7156526077883281621L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  @Column(name = "number_of_seat")
  private Integer numberOfSeat;
  @Column(name = "seat_bus_type")
  private Integer seatBusType;
  private Integer status;
  private Integer floor;
  @Column(name = "number_of_row")
  private Integer numberOfRow;
  @Column(name = "number_of_column")
  private Integer numberOfColumn;
  @Type(type = "json")
  @Column(columnDefinition = "json", name = "seat_diagram")
  private SeatDiagramList seatDiagram;
}
