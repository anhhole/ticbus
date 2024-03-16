package com.ticbus.backend.model.specification;

import com.ticbus.backend.model.*;
import com.ticbus.backend.payload.request.TripListRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author AnhLH
 */
@Component
public class TripSpecification extends
        BaseSpecification<Trip, TripListRequest> {

    public static Specification<Trip> textInAllColumns(String text) {

        if (!text.contains("%")) {
            text = "%" + text + "%";
        }
        final String finalText = text;

        return new Specification<Trip>() {
            @Override
            public Predicate toPredicate(Root<Trip> root, CriteriaQuery<?> cq,
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
    public Specification<Trip> getFilter(TripListRequest request) {
        return (root, query, cb) -> {
            query.distinct(true);
            return where(
                    where(nameContains(request.getName()))
                            .or(departureNameContains(request.getDepartureName()))
                            .or(destinationNameContains(request.getDestinationName()))
                            .or(departureAddressContains(request.getDepartureAddress()))
                            .or(destinationAddressContains(request.getDestinationAddress()))
                            .or(statusEqual(Optional.ofNullable(request.getStatus()).orElse(-1)))
            )
                    .toPredicate(root, query, cb);
        };
    }

    private Specification<Trip> nameContains(String name) {
        return tripAttributeContains("name", name);
    }

    private Specification<Trip> departureNameContains(String name) {
        return departureNameAttributeContains("name", name);
    }

    private Specification<Trip> destinationNameContains(String name) {
        return destinationNameAttributeContains("name", name);
    }
    private Specification<Trip> departureAddressContains(String name) {
        return departureNameAttributeContains("address", name);
    }

    private Specification<Trip> destinationAddressContains(String name) {
        return destinationNameAttributeContains("address", name);
    }

    private Specification<Trip> statusEqual(int status) {
        return tripAttributeEqual("status", status);
    }

    private Specification<Trip> tripAttributeContains(String attribute,
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

    private Specification<Trip> tripAttributeEqual(String attribute, int value) {
        return (root, query, cb) -> {
            if (value == -1) {
                return null;
            }

            return cb.equal(cb.lower(root.get(attribute)), value);
        };
    }

    private Specification<Trip> departureNameAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            Join<Trip, Departure> departure = root.join("departure", JoinType.INNER);

            return cb.like(
                    cb.lower(departure.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<Trip> destinationNameAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            Join<Trip, Destination> bus = root.join("destination", JoinType.INNER);

            return cb.like(
                    cb.lower(bus.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }
}
