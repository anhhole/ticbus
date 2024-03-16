package com.ticbus.backend.model.specification;


import com.ticbus.backend.model.Scheduler;
import com.ticbus.backend.model.Trip;
import com.ticbus.backend.payload.request.SchedulerListRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specifications.where;

@Component
public class SchedulerSpecification extends BaseSpecification<Scheduler, SchedulerListRequest>{


    public static Specification<Scheduler> textInAllColumns(String text) {

        if (!text.contains("%")) {
            text = "%" + text + "%";
        }
        final String finalText = text;

        return new Specification<Scheduler>() {
            @Override
            public Predicate toPredicate(Root<Scheduler> root, CriteriaQuery<?> cq,
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
    public Specification<Scheduler> getFilter(SchedulerListRequest request) {
        return (root, query, cb) -> {
            query.distinct(true);
            return where(
                    where(nameContains(request.getTripName()))
                            .or(statusEqual(Optional.ofNullable(request.getStatus()).orElse(-1)))
            )
                    .toPredicate(root, query, cb);
        };
    }

    private Specification<Scheduler> nameContains(String name) {
        return schedulerTripAttributeEqual("name", name);
    }


    private Specification<Scheduler> statusEqual(int status) {
        return schedulerAttributeEqual("status", status);
    }



    private Specification<Scheduler> schedulerAttributeContains(String attribute,
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


    private Specification<Scheduler> schedulerAttributeEqual(String attribute, int value) {
        return (root, query, cb) -> {
            if (value == -1) {
                return null;
            }

            return cb.equal(cb.lower(root.get(attribute)), value);
        };
    }

    private Specification<Scheduler> schedulerTripAttributeEqual(String attribute, String  value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }
            Join<Scheduler,Trip> scheduler =root.join("trip",JoinType.INNER);

            return cb.like(
                    cb.lower(scheduler.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

}
