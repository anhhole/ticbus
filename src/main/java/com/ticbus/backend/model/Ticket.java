package com.ticbus.backend.model;


import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_ticket")
public class Ticket extends BaseModel implements Serializable {

  private static final long serialVersionUID = 7156526077883281626L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String row;
  private String columns;
  private String price;
  @ManyToOne(cascade = CascadeType.ALL, targetEntity = Trip.class)
  @JoinColumn(name = "trip_id")
  private Integer tripId;

  @ManyToOne(cascade = CascadeType.ALL, targetEntity = Customer.class)
  @JoinColumn(name = "customer_id")
  private Integer customerId;
  private Integer status;
  @Column(name = "booked_on")
  private Integer bookedOn;

}
