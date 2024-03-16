package com.ticbus.backend.model.specification;

import static org.springframework.data.jpa.domain.Specifications.where;

import com.ticbus.backend.model.Employee;
import com.ticbus.backend.payload.request.EmployeeListRequest;
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
public class EmployeeSpecification extends BaseSpecification<Employee, EmployeeListRequest> {

  public static Specification<Employee> textInAllColumns(String text) {

    if (!text.contains("%")) {
      text = "%" + text + "%";
    }
    final String finalText = text;

    return new Specification<Employee>() {
      @Override
      public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> cq,
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
  public Specification<Employee> getFilter(EmployeeListRequest request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(nameContains(request.getName()))
              .or(phoneContains(request.getPhone()))
              .or(emailContains(request.getEmail()))
              .or(addressContains(request.getAddress()))
      )
          .toPredicate(root, query, cb);
    };
  }

  private Specification<Employee> nameContains(String firstName) {
    return employeeAttributeContains("name", firstName);
  }

  private Specification<Employee> phoneContains(String phone) {
    return employeeAttributeContains("phone", phone);
  }

  private Specification<Employee> emailContains(String email) {
    return employeeAttributeContains("email", email);
  }

  private Specification<Employee> addressContains(String address) {
    return employeeAttributeContains("address", address);
  }

  private Specification<Employee> employeeAttributeContains(String attribute, String value) {
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
}
