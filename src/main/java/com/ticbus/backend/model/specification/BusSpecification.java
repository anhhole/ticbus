package com.ticbus.backend.model.specification;

import com.ticbus.backend.model.Bus;
import com.ticbus.backend.model.BusSeatDiagram;
import com.ticbus.backend.payload.request.BusListRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;

import java.util.Optional;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author AnhLH
 */
@Component
public class BusSpecification extends BaseSpecification<Bus, BusListRequest> {
  public static Specification<Bus> textInAllColumns(String text) {

    if (!text.contains("%")) {
      text = "%"+text+"%";
    }
    final String finalText = text;

    return new Specification<Bus>() {
      @Override
      public Predicate toPredicate(Root<Bus> root, CriteriaQuery<?> cq, CriteriaBuilder builder) {
        return builder.or(root.getModel().getDeclaredSingularAttributes().stream().filter(a-> {
              if (a.getJavaType().getSimpleName().equalsIgnoreCase("string")) {
                return true;
              }
              else {
                return false;
              }}).map(a -> builder.like(root.get(a.getName()), finalText)
            ).toArray(Predicate[]::new)
        );
      }
    };
  }

  @Override
  public  Specification<Bus> getFilter(BusListRequest request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(nameContains(request.getLicensePlates()))
              .or(statusEqual(Optional.ofNullable(request.getStatus()).orElse(-1)))
              .or(numberOfSeatEqual(Optional.ofNullable(request.getNumberOfSeat()).orElse(-1)))
                  .or(numberOfFloorEqual(Optional.ofNullable(request.getNumberOfFloor()).orElse(-1)))
                  .or(seatBusTypeEqual(Optional.ofNullable(request.getSeatBusType()).orElse(-1)))
      )
          .toPredicate(root, query, cb);
    };
  }
  private Specification<Bus> nameContains(String firstName) {
    return employeeAttributeContains("license_plates", firstName);
  }

  private Specification<Bus> numberOfSeatEqual(int numberOfSeat) {

    return busSeatDiagramAttributeEqual("numberOfSeat", numberOfSeat);
  }
  private Specification<Bus> numberOfFloorEqual(int numberOfFloor) {

    return busSeatDiagramAttributeEqual("numberOfFloor", numberOfFloor);
  }

  private Specification<Bus>  seatBusTypeEqual( int seatBusType) {
    return busSeatDiagramAttributeEqual("seatBusType", seatBusType);
  }
  private Specification<Bus> statusEqual(int status) {
    return busSeatDiagramAttributeEqual("status", status);
  }
  private Specification<Bus> employeeAttributeContains(String attribute, String value) {
    return (root, query, cb) -> {
      if(value == null) {
        return null;
      }

      return cb.like(
          cb.lower(root.get(attribute)),
          containsLowerCase(value)
      );
    };
  }
  private Specification<Bus> busAttributeEqual(String attribute, int value) {
    return (root, query, cb) -> {
      if (value == -1) {
        return null;
      }

      return cb.equal(cb.lower(root.get(attribute)), value);
    };
  }
  private Specification<Bus> busSeatDiagramAttributeEqual(String attribute, int value) {
    return (root, query, cb) -> {
      if(value == -1) {
        return null;
      }
      Join<Bus,BusSeatDiagram> bus =root.join("seatBusDiagram",JoinType.INNER);

      return cb.equal(
              cb.lower(bus.get(attribute)),
              value
      );
    };
  }
}
