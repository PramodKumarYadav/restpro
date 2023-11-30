package org.powertester.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtils {
  private DateUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static String getDateAsString(String format) {
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    return now.format(formatter);
  }
}
