package com.ticbus.backend.model.specification;

import static org.springframework.data.jpa.domain.Specifications.where;

import com.ticbus.backend.model.City;
import com.ticbus.backend.payload.request.CityListRequest;

import javax.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CitySpecification extends BaseSpecification<City, CityListRequest> {

  public static Specification<City> textInAllColumns(String text) {

    if (!text.contains("%")) {
      text = "%" + text + "%";
    }
    final String finalText = text;

    return new Specification<City>() {
      @Override
      public Predicate toPredicate(Root<City> root, CriteriaQuery<?> cq, CriteriaBuilder builder) {
        cq.distinct(true);
        return builder.or(root.getModel().getDeclaredSingularAttributes().stream().filter(a -> {
              return a.getJavaType().getSimpleName().equalsIgnoreCase("string");
            }).map(a -> builder.like(root.get(a.getName()), finalText)
            ).toArray(Predicate[]::new)
        );
      }
    };
  }

  @Override
  public Specification<City> getFilter(CityListRequest request) {
    return (root, query, cb) -> {
      query.distinct(true);

      return where(
          where(cityContains(request.getCity()))
              .or(provinceContains(request.getProvince()))
              .or(statusContains(request.getStatus()))
      )
          .toPredicate(root, query, cb);
    };
  }

  private Specification<City> cityContains(String city) {
    return customerAttributeContains("city", city);
  }

  private Specification<City> provinceContains(String province) {
    return customerAttributeContains("province", province);
  }

  private Specification<City> statusContains(Integer status) {
    return customerAttributeNumberTypeContains("status", status);
  }


  private Specification<City> customerAttributeContains(String attribute, Object value) {
    return (root, query, cb) -> {
      if (value == null) {
        return null;
      }

      return cb.like(
          cb.lower(root.get(attribute)),
          containsLowerCase(value.toString())
      );
    };
  }

    private Specification<City> customerAttributeNumberTypeContains(String attribute, Object value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }

            return cb.equal(
                    cb.lower(root.get(attribute)),
                    containsNumberType((Integer) value)
            );
        };
    }
}
