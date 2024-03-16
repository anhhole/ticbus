package com.ticbus.backend.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_trip")
public class Trip extends BaseModel implements Serializable {

  private static final long serialVersionUID = 7156526077883281622L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private Integer status;
  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Departure.class)
  @JoinColumn(name = "departure")
  private Departure departure;
  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Destination.class)
  @JoinColumn(name = "destination")
  private Destination destination;

}
