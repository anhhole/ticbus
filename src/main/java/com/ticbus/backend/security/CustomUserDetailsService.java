package com.ticbus.backend.security;

import com.ticbus.backend.model.Customer;
import com.ticbus.backend.model.Employee;
import com.ticbus.backend.repository.CustomerRepository;
import com.ticbus.backend.repository.EmployeeRepository;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AnhLH
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  EmployeeRepository employeeRepository;
  @Autowired
  CustomerRepository customerRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String phone)
      throws UsernameNotFoundException {
    Employee user = employeeRepository.findByPhone(phone).orElse(null);
    if (!Objects.isNull(user)) {
      return UserPrincipal.createCMS(user);
    }
    Customer customer = customerRepository.findByPhone(phone).orElse(null);
    if (!Objects.isNull(customer)) {
      return UserPrincipal.create(customer);
    }
    return null;
  }

}