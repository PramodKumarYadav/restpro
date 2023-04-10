import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.typesafe.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import io.restassured.specification.RequestSpecification;
import jakarta.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.powertester.annotations.FailingTest;
import org.powertester.annotations.FlakyTest;
import org.powertester.annotations.SmokeTest;
import org.powertester.config.TestEnvFactory;
import setup.TestSetup;

@Slf4j
public class TestSandbox extends TestSetup {
  @Test
  public void postTest() {
    String json = "{\n" +
            "    \"firstname\" : \"Jim\",\n" +
            "    \"lastname\" : \"Brown\",\n" +
            "    \"totalprice\" : 111,\n" +
            "    \"depositpaid\" : true,\n" +
            "    \"bookingdates\" : {\n" +
            "        \"checkin\" : \"2018-01-01\",\n" +
            "        \"checkout\" : \"2019-01-01\"\n" +
            "    },\n" +
            "    \"additionalneeds\" : \"Breakfast\"\n" +
            "}";

    RequestSpecification specification = new RequestSpecBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-type", "application/json")
            .setBaseUri("https://restful-booker.herokuapp.com")
            .setBody(json)
             .build();

    given()
            .header("Accept", "application/json")
            .header("Content-type", "application/json")
            .baseUri("https://restful-booker.herokuapp.com")
            .body(json)
            .when()
            .post("/booking")
            .then()
            .extract()
            .response()
            .prettyPeek()
            .then()
            .statusCode(200)
    ;
  }

  @Test
  public void getTest() {
    when().
            get("https://restful-booker.herokuapp.com/booking/181").
            then()
            .extract()
            .response()
            .prettyPeek()
            .then()
            .statusCode(200)
    ;
  }



  @Test

  @RepeatedTest(10)
  public void assertThatWeCanGetUserConfig() {
    final Config CONFIG = TestEnvFactory.getInstance().getConfig();
    final String expectedEnv = CONFIG.getString("TEST_ENV");
    assertAll(
        "Config test",
        () -> assertEquals(expectedEnv, CONFIG.getString("TEST_ENV"), "TEST_ENV"),
        () ->
            assertEquals(
                "/employee/create",
                CONFIG.getString("CREATE_EMPLOYEE_ENDPOINT"),
                "CREATE_EMPLOYEE_ENDPOINT"),
        () ->
            assertEquals(
                expectedEnv.toLowerCase() + "-admin-user",
                CONFIG.getString("ADMIN_NAME"),
                "ADMIN_NAME"),
        () -> assertFalse(CONFIG.getBoolean("TOGGLE"), "TOGGLE"),
        () -> assertEquals(10, CONFIG.getInt("NR_OF_USERS"), "NR_OF_USERS"),
        () -> assertEquals(123.456, CONFIG.getDouble("PRICE"), "PRICE"),
        () ->
            assertEquals(
                expectedEnv.toLowerCase() + "-user", CONFIG.getString("USER_NAME"), "USER_NAME"));
  }

  /** a very basic test */
  @SmokeTest
  void assertThatTrueIsTrue() {
    assertTrue(true, "true is true");
  }

  @FailingTest
  void assertThatADayIsADay() {
    assertEquals("day", "night", "true is true");
  }

  @DisplayName("flaky test")
  @FlakyTest
  void createAFlakyTestCase() {
    long currentTimeStamp = System.currentTimeMillis();
    log.debug("currentTimeStamp: {}", currentTimeStamp);
    if (currentTimeStamp % 2 == 0) {
      assertTrue(true, "time is even");
    } else {
      assertTrue(false, "time is odd");
    }
  }
}
