package org.powertester.authtoken;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.typesafe.config.Config;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.powertester.config.TestConfig;

@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder(setterPrefix = "set")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthBody {
  private String username;
  private String password;

  private static final Config CONFIG = TestConfig.getInstance().getConfig();

  public static AuthBody getInstance() {
    AuthBody authBody =
        AuthBody.builder()
            .setUsername(CONFIG.getString("ADMIN_USERNAME"))
            .setPassword(CONFIG.getString("ADMIN_PASSWORD"))
            .build();

    return authBody;
  }
}
