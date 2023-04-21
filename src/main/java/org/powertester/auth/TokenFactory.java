package org.powertester.auth;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.powertester.auth.singletontokens.AdminToken;
import org.powertester.auth.singletontokens.MaintainerToken;

@Slf4j
public class TokenFactory {
  public static String getTokenFor(Scope scope) {
    AuthBody authBody;
    switch (scope) {
      case GUEST:
        return "";
      case MAINTAINER:
        return MaintainerToken.getInstance().getMaintainerToken();
      case ADMIN:
        return AdminToken.getInstance().getAdminToken();
      default:
        throw new IllegalStateException(
            "Not a valid scope. Pick a scope from " + Arrays.toString(Scope.values()));
    }
  }
}
