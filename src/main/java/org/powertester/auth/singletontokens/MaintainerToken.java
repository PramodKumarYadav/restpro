package org.powertester.auth.singletontokens;

import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.powertester.auth.AuthBody;
import org.powertester.config.TestConfig;

@Slf4j
public class MaintainerToken {
  private static final Config CONFIG = TestConfig.getInstance().getConfig();
  private static final MaintainerToken UNIQUE_INSTANCE = new MaintainerToken();

  private String maintainerToken;

  private MaintainerToken() {
    AuthBody authBody =
        AuthBody.builder()
            .setUsername(CONFIG.getString("MAINTAINER_USERNAME"))
            .setPassword(CONFIG.getString("MAINTAINER_PASSWORD"))
            .build();

    maintainerToken = AuthAPI.getToken(authBody);
  }

  public static MaintainerToken getInstance() {
    return UNIQUE_INSTANCE;
  }

  public String getMaintainerToken() {
    return maintainerToken;
  }
}
