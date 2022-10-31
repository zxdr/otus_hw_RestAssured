package user;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.NewUserApi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetUserTest {
    private static final String PATH_PARAM = "/user1";

    @Test
    // @negative
    // uses not existed userName, checks if we get status code equals to 404 and response message that user not found
    public void checkGetUserResponse() {
        NewUserApi newUserApi = new NewUserApi();
        Integer actualStatus = newUserApi.getUser(PATH_PARAM).extract().statusCode();
        String actualMessage = newUserApi.getUser(PATH_PARAM).extract().body().asString();

        Assertions.assertAll(
                () -> assertThat("Actual status code is not correct", actualStatus.equals(404)),
                () -> assertThat("Actual response is not correct", actualMessage.contains("User not found")));
    }

    @Test
    // checks if response body is correct according to ApiResponse described in https://petstore.swagger.io/#/
    // using json schema
    public void checkGetUserResponseWithinJsonSchema() {
        NewUserApi newUserApi = new NewUserApi();
        newUserApi.getUser(PATH_PARAM)
                .body("code", equalTo(1))
                .body("type", equalTo("error"))
                .body("message", equalTo("User not found"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/GetUser.json"));
    }
}
