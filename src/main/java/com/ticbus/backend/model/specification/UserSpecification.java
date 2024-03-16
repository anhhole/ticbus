package com.ticbus.backend.model.specification;


import static org.springframework.data.jpa.domain.Specifications.where;

import com.ticbus.backend.model.Customer;
import com.ticbus.backend.payload.request.CustomerListRequest;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecification extends BaseSpecification<Customer, CustomerListRequest> {

  public static Specification<Customer> textInAllColumns(String text) {
    if (!text.contains("%")) {
      text = "%" + text + "%";
    }
    final String finalText = text;

    return new Specification<Customer>() {
      @Override
      public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> cq,
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
  public Specification<Customer> getFilter(CustomerListRequest request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(nameContains(request.getName()))
              .or(phoneContains(request.getPhone()))
              .or(emailContains(request.getMail()))
              .or(addressContains(request.getAddress()))
      )
          .toPredicate(root, query, cb);
    };
  }

  private Specification<Customer> nameContains(String firstName) {
    return customerAttributeContains("name", firstName);
  }

  private Specification<Customer> phoneContains(String phone) {
    return customerAttributeContains("phone", phone);
  }

  private Specification<Customer> emailContains(String email) {
    return customerAttributeContains("mail", email);
  }

  private Specification<Customer> addressContains(String address) {
    return customerAttributeContains("address", address);
  }

  private Specification<Customer> customerAttributeContains(String attribute, String value) {
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
