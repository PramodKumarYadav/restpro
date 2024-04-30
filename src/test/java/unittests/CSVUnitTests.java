package unittests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.powertester.annotations.CsvTest;
import org.powertester.annotations.UnitTest;

@UnitTest
@Slf4j
public class CSVUnitTests {

  @CsvTest
  @CsvFileSource(files = "src/test/resources/testdata/data.csv", numLinesToSkip = 1)
  void testWithCsvFileSource(String testName, String userID, String access) {
    log.info("userID: " + userID + ", access: " + access);
  }
}
