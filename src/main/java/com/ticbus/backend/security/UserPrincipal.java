package com.ticbus.backend.security;

import com.ticbus.backend.model.Customer;
import com.ticbus.backend.model.Employee;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author AnhLH
 */
@Getter
@Setter
public class UserPrincipal implements UserDetails {

  private Integer id;
  private String username;
  private String phone;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public UserPrincipal(Integer id, String username, String phone, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.phone = phone;
    this.authorities = authorities;
  }

  public UserPrincipal(Integer id, String phone, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.phone = phone;
    this.authorities = authorities;
  }

  public static UserPrincipal createCMS(Employee customer) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("USER"));
    authorities.add(new SimpleGrantedAuthority("ADMIN"));
    return new UserPrincipal(
        customer.getId(),
        customer.getName(),
        customer.getPhone(),
        customer.getPassword(),
        authorities
    );
  }

  public static UserPrincipal create(Customer customer) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    return new UserPrincipal(
        customer.getId(),
        customer.getName(),
        customer.getPhone(),
        customer.getPassword(),
        authorities

    );
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserPrincipal that = (UserPrincipal) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
