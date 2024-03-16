package com.ticbus.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author AnhLH
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_scheduler")
public class Scheduler extends BaseModel implements Serializable {

  private static final long serialVersionUID = 7156526077883281622L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @OneToOne(cascade = CascadeType.MERGE, targetEntity = Bus.class)
  @JoinColumn(name = "bus_id")
  private Bus bus;
  @OneToOne(cascade = CascadeType.MERGE, targetEntity = Trip.class)
  @JoinColumn(name = "trip_id")
  private Trip trip;
  @OneToMany(cascade = CascadeType.ALL, targetEntity = Ticket.class,fetch = FetchType.EAGER)
  private List<Ticket> tickets;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "start_time")
  private Date startTime;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Date endTime;
  private Integer status;
}
