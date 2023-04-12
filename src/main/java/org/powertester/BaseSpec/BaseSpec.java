package org.powertester.BaseSpec;

import io.restassured.builder.RequestSpecBuilder;

public class BaseSpec {
    public static RequestSpecBuilder get() {
        return new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .setBaseUri("https://restful-booker.herokuapp.com");
    }

    public static RequestSpecBuilder get(String token) {
        return get()
                .addCookie("token", token);
    }
}
