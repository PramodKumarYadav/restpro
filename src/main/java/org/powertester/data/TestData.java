package org.powertester.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TestData {
  private Map<String, String> keyValueMap; // Ex: amount: 100, 200, 300
  private Map<String, String> keyTypeMap; // Ex: amount: output, input, io

  public TestData(Map<String, String> keyTypeMap, Map<String, String> keyValueMap) {
    this.keyTypeMap = keyTypeMap;
    this.keyValueMap = keyValueMap;
  }

  public TestData() {
    this(new HashMap<>(), new HashMap<>());
  }

  public void setKey(String key, String value, String type) {
    keyValueMap.put(key, value);
    keyTypeMap.put(key, type);
  }

  public void setKey(String key, String value) {
    setKey(key, value, "default");
  }

  // Get trimmed keys
  public Set<String> getKeys() {
    return keyValueMap.keySet().stream().map(String::trim).collect(Collectors.toSet());
  }

  public Map<String, String> getKeyTypeMap() {
    return keyTypeMap;
  }

  public Map<String, String> getKeyValueMap() {
    return keyValueMap;
  }

  public void setKeyValueMap(Map<String, String> keyValueMap) {
    this.keyValueMap = keyValueMap;
  }

  public void setKeyTypeMap(Map<String, String> keyTypeMap) {
    this.keyTypeMap = keyTypeMap;
  }

  public String getValue(String key) {
    return keyValueMap.get(key);
  }

  public String getType(String key) {
    return keyTypeMap.get(key);
  }

  public String toString() {
    return "keyValueMap: " + keyValueMap + "\nkeyTypeMap: " + keyTypeMap;
  }
}
