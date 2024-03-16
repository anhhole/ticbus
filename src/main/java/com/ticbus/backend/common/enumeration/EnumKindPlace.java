package com.ticbus.backend.common.enumeration;

/**
 * @author AnhLH
 */
public enum  EnumKindPlace {
  STOP_STATION(3,"STOP_STATION"),
  DESTINATION(2, "DESTINATION"),
  DEPARTURE(1, "DEPARTURE");

  private int id;
  private String roleName;

  EnumKindPlace(int id, String name) {
    this.id = id;
    this.roleName = name;
  }

  public static EnumKindPlace getById(int id) {
    for (EnumKindPlace sex : EnumKindPlace.values()) {
      if (sex.id == id) {
        return sex;
      }
    }
    return null;
  }

  public static EnumKindPlace getByName(String name) {
    for (EnumKindPlace role : EnumKindPlace.values()) {
      if (role.roleName.equalsIgnoreCase(name)) {
        return role;
      }
    }
    return null;
  }
  public int getId(){return this.id;}
}
