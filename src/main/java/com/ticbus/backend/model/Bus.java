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
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_bus")
public class Bus extends BaseModel implements Serializable {
    private static final long serialVersionUID = 7156526077883281621L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "license_plates")
    private String licensePlates;
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = BusSeatDiagram.class)
    @JoinColumn(name = "seat_bus_diagram")
    private BusSeatDiagram seatBusDiagram;
    private Integer status;
}
