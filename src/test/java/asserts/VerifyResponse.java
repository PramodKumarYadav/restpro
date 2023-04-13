package asserts;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

public abstract class VerifyResponse<SELF_TYPE extends VerifyResponse<?>> {
    protected SELF_TYPE selfType;
    protected Response response;
    protected SoftAssertions softAssertions;

    protected VerifyResponse(Class<SELF_TYPE> selfType, Response response) {
        this.selfType = selfType.cast(this);
        this.response = response;
        this.softAssertions = new SoftAssertions();
    }

    public SELF_TYPE statusCodeIs(int statusCode) {
        Assertions.assertThat(response.getStatusCode())
                .describedAs("statusCode")
                .isEqualTo(statusCode);

        return selfType;
    }

    public SELF_TYPE containsValue(String value) {
        softAssertions.assertThat(response.getBody().asString())
                .describedAs("responseBody")
                .contains(value);

        return selfType;
    }

    public void assertAll() {
        softAssertions.assertAll();
    }
}
