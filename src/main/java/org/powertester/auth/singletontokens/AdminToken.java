package org.powertester.auth.singletontokens;

import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.powertester.auth.AuthBody;
import org.powertester.config.TestConfig;

@Slf4j
public class AdminToken {
  private static final Config CONFIG = TestConfig.getInstance().getConfig();
  private static final AdminToken UNIQUE_INSTANCE = new AdminToken();

  private String adminToken;

  private AdminToken() {
    AuthBody authBody =
        AuthBody.builder()
            .setUsername(CONFIG.getString("ADMIN_USERNAME"))
            .setPassword(CONFIG.getString("ADMIN_PASSWORD"))
            .build();

    adminToken = AuthAPI.getToken(authBody);
  }

  public static AdminToken getInstance() {
    return UNIQUE_INSTANCE;
  }

  public String getAdminToken() {
    return adminToken;
  }
}
