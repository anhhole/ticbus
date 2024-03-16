package com.ticbus.backend.common.enumeration;

/**
 * @author AnhLH
 */
public enum  EnumSearch {
  NAME(1, "1"),
  ADDRESS(2, "2"),
  ID(3, "3");

  private int id;
  private String name;

  EnumSearch(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static EnumSearch getById(int id) {
    for (EnumSearch sex : EnumSearch.values()) {
      if (sex.id == id) {
        return sex;
      }
    }
    return null;
  }

  public static EnumSearch getByName(String name) {
    for (EnumSearch sex : EnumSearch.values()) {
      if (sex.name.equalsIgnoreCase(name)) {
        return sex;
      }
    }
    return null;
  }
  public String getName(){return this.name;}
}
