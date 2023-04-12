package org.powertester.BaseSpec;

import com.typesafe.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import org.powertester.config.TestConfig;

public class BaseSpec {
    private static final Config CONFIG = TestConfig.getInstance().getConfig();
    public static RequestSpecBuilder get() {
        return new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .setBaseUri(CONFIG.getString("BASE_URL"));
    }

    public static RequestSpecBuilder get(String token) {
        return get()
                .addCookie("token", token);
    }
}
