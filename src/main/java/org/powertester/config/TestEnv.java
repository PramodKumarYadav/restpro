package org.powertester.config;

public enum TestEnv {
  LOCALHOST("localhost"),
  DEVELOP("develop"),
  STAGING("staging");

  TestEnv(String value) {
    this.value = value;
  }

  private String value;

  public String getValue() {
    return value;
  }

  public static TestEnv getEnumByValue(String value) {
    for(TestEnv testEnv: TestEnv.values()){
      if(testEnv.getValue().equalsIgnoreCase(value)){
        return testEnv;
      }
    }

    throw new IllegalStateException("No enum constant with value: " + value);
  }
}
