package org.powertester.basespec;

import static org.powertester.auth.Scope.ADMIN;
import static org.powertester.auth.Scope.MAINTAINER;

import com.typesafe.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import java.util.Arrays;
import org.powertester.auth.Scope;
import org.powertester.auth.TokenFactory;
import org.powertester.config.TestConfig;

public class SpecFactory {
  private static final Config CONFIG = TestConfig.getInstance().getConfig();

  public static RequestSpecBuilder getSpecFor(Scope scope) {
    switch (scope) {
      case GUEST:
        return get();
      case MAINTAINER:
        return get(TokenFactory.getInstance().getTokenFor(MAINTAINER));
      case ADMIN:
        return get(TokenFactory.getInstance().getTokenFor(ADMIN));
      default:
        throw new IllegalStateException(
            "Not a valid scope. Pick a scope from " + Arrays.toString(Scope.values()));
    }
  }

  private static RequestSpecBuilder get() {
    return new RequestSpecBuilder()
        .addHeader("Content-Type", "application/json")
        .addHeader("Accept", "application/json")
        .setBaseUri(CONFIG.getString("BASE_URL"));
  }

  private static RequestSpecBuilder get(String token) {
    return get().addCookie("token", token);
  }
}
