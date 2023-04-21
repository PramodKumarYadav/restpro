package org.powertester.basespec;

import io.restassured.builder.RequestSpecBuilder;
import java.util.Arrays;
import org.powertester.auth.AuthAPI;
import org.powertester.auth.Scope;

public class SpecFactory {
  public static RequestSpecBuilder getSpecFor(Scope scope) {
    switch (scope) {
      case GUEST:
        return BaseSpec.get();
      case MAINTAINER:
      case ADMIN:
        return BaseSpec.get(AuthAPI.getToken());
      default:
        throw new IllegalStateException(
            "Not a valid scope. Pick a scope from " + Arrays.toString(Scope.values()));
    }
  }
}
