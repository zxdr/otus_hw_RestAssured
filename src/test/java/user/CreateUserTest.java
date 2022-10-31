package user;

import dto.UserDto;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.NewUserApi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {

    @Test
    // checks if statusCode is 200 and response body contains correct values: 202, 200, unknown
    public void checkCreateUserResponse() {
        NewUserApi newUserApi = new NewUserApi();
        long id = 202L;
        UserDto user = UserDto.builder()
                .userStatus(101L)
                .email("email")
                .id(id)
                .firstName("Autotest firstName")
                .lastName("Autotest lastName")
                .phone("9099998877")
                .build();

        Integer actualStatus = newUserApi.createUser(user).extract().statusCode();
        String actualResponse = newUserApi.createUser(user).extract().body().asString();
        Assertions.assertAll(
                () -> assertThat("Actual status code is not correct", actualStatus.equals(200)),
                () -> assertThat("Wrong message", actualResponse.contains(Long.toString(id))),
                () -> assertThat("Wrong type", actualResponse.contains("unknown")),
                () -> assertThat("Wrong code", actualResponse.contains("200")));
    }

    @Test
    // checks if response body is correct according to ApiResponse described in https://petstore.swagger.io/#/
    // using json schema
    public void checkCreateUserResponseWithinJsonSchema() {
        NewUserApi newUserApi = new NewUserApi();
        long id = 202L;
        UserDto user = UserDto.builder()
                .userStatus(101L)
                .email("email")
                .id(id)
                .firstName("Autotest firstName")
                .lastName("Autotest lastName")
                .phone("9099998877")
                .build();

        newUserApi.createUser(user)
                .statusCode(200)
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", equalTo(Long.toString(id)))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"));
    }
}
