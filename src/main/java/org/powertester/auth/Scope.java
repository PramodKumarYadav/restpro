package org.powertester.auth;

public enum Scope {
  GUEST("read"),
  MAINTAINER("write"),
  ADMIN("delete");

  private String value;

  Scope(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
