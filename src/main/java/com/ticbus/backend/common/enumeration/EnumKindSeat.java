package com.ticbus.backend.common.enumeration;

/**
 * @author AnhLH
 */
public enum EnumKindSeat {
  SEAT(1, "SEAT"),
  BED_SEAT(2, "BED_SEAT");

  private int id;
  private String name;

  EnumKindSeat(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static EnumKindSeat getById(int id) {
    for (EnumKindSeat sex : EnumKindSeat.values()) {
      if (sex.id == id) {
        return sex;
      }
    }
    return null;
  }

  public static EnumKindSeat getByName(String name) {
    for (EnumKindSeat role : EnumKindSeat.values()) {
      if (role.name.equalsIgnoreCase(name)) {
        return role;
      }
    }
    return null;
  }
  public int getId(){return this.id;}
}
