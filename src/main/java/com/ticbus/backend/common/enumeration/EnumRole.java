package com.ticbus.backend.common.enumeration;

/**
 * @author AnhLH
 */
public enum EnumRole {
  USER(2, "USER"),
  ADMIN(1, "ADMIN");

  private int id;
  private String roleName;

  EnumRole(int id, String name) {
    this.id = id;
    this.roleName = name;
  }

  public static EnumRole getById(int id) {
    for (EnumRole sex : EnumRole.values()) {
      if (sex.id == id) {
        return sex;
      }
    }
    return null;
  }

  public static EnumRole getByName(String name) {
    for (EnumRole role : EnumRole.values()) {
      if (role.roleName.equalsIgnoreCase(name)) {
        return role;
      }
    }
    return null;
  }
  public int getId(){return this.id;}
}
