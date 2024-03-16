package com.ticbus.backend.model.specification;

import static org.springframework.data.jpa.domain.Specifications.where;

import com.ticbus.backend.model.BusSeatDiagram;
import com.ticbus.backend.payload.request.BusSeatDiagramListRequest;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * @author AnhLH
 */
@Component
public class BusSeatDiagramSpecification extends
    BaseSpecification<BusSeatDiagram, BusSeatDiagramListRequest> {

  public static Specification<BusSeatDiagram> textInAllColumns(String text) {

    if (!text.contains("%")) {
      text = "%" + text + "%";
    }
    final String finalText = text;

    return new Specification<BusSeatDiagram>() {
      @Override
      public Predicate toPredicate(Root<BusSeatDiagram> root, CriteriaQuery<?> cq,
          CriteriaBuilder builder) {
        return builder.or(root.getModel().getDeclaredSingularAttributes().stream().filter(a -> {
            return a.getJavaType().getSimpleName().equalsIgnoreCase("string");
            }).map(a -> builder.like(root.get(a.getName()), finalText)
            ).toArray(Predicate[]::new)
        );
      }
    };
  }

  @Override
  public Specification<BusSeatDiagram> getFilter(BusSeatDiagramListRequest request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(nameContains(request.getName()))
              .or(numberOfSeatEqual(Optional.ofNullable(request.getNumberOfSeat()).orElse(-1)))
              .or(numberOfRowEqual(Optional.ofNullable(request.getNumberOfRow()).orElse(-1)))
              .or(numberOfColumnEqual(Optional.ofNullable(request.getNumberOfColumn()).orElse(-1)))
              .or(seatBusTypeEqual(Optional.ofNullable(request.getSeatBusType()).orElse(-1)))
              .or(statusEqual(Optional.ofNullable(request.getStatus()).orElse(-1)))
              .or(floorEqual(Optional.ofNullable(request.getFloor()).orElse(-1)))
      )
          .toPredicate(root, query, cb);
    };
  }

  private Specification<BusSeatDiagram> nameContains(String name) {
    return busSeatDiagramAttributeContains("name", name);
  }

  private Specification<BusSeatDiagram> numberOfSeatEqual(int numberOfSeat) {
    return busSeatDiagramAttributeEqual("numberOfSeat", numberOfSeat);
  }

  private Specification<BusSeatDiagram> numberOfRowEqual(int numberOfSeat) {
    return busSeatDiagramAttributeEqual("numberOfRow", numberOfSeat);
  }

  private Specification<BusSeatDiagram> numberOfColumnEqual(int numberOfSeat) {
    return busSeatDiagramAttributeEqual("numberOfColumn", numberOfSeat);
  }

  private Specification<BusSeatDiagram> seatBusTypeEqual(int seatBusType) {
    return busSeatDiagramAttributeEqual("seatBusType", seatBusType);
  }

  private Specification<BusSeatDiagram> statusEqual(int status) {
    return busSeatDiagramAttributeEqual("status", status);
  }

  private Specification<BusSeatDiagram> floorEqual(int floor) {
    return busSeatDiagramAttributeEqual("floor", floor);
  }

  private Specification<BusSeatDiagram> busSeatDiagramAttributeContains(String attribute,
      String value) {
    return (root, query, cb) -> {
      if (value == null) {
        return null;
      }

      return cb.like(
          cb.lower(root.get(attribute)),
          containsLowerCase(value)
      );
    };
  }

  private Specification<BusSeatDiagram> busSeatDiagramAttributeEqual(String attribute, int value) {
    return (root, query, cb) -> {
      if (value == -1) {
        return null;
      }

      return cb.equal(cb.lower(root.get(attribute)), value);
    };
  }
}
