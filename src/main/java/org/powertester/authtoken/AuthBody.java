package org.powertester.authtoken;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

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

    public static AuthBody getInstance() {
        AuthBody authBody = AuthBody.builder()
                .setUsername("admin")
                .setPassword("password123")
                .build();

        return authBody;
    }
}
