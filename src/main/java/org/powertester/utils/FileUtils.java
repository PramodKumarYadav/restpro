package org.powertester.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
  private FileUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static void createDirectory(String directoryPath) {
    try {
      Path path = Paths.get(directoryPath);
      Files.createDirectory(path);
    } catch (Exception exception) {
      exception.printStackTrace();
      throw new IllegalStateException("Unable to create directory for path :" + directoryPath);
    }
  }
}
