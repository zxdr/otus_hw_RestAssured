package services;

import dto.UserDto;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class NewUserApi {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String USER = "/user";
    //    private static final String PATH_PARAM = "/user1";
    private RequestSpecification spec;

    public NewUserApi() {
        spec = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();
    }

    public ValidatableResponse createUser(UserDto user) {

        return given(spec)
                .body(user)
                .when()
                .post(USER)
                .then()
                .log().all();
    }

    public ValidatableResponse getUser(String param) {

        return given(spec)
                .when()
                .get(USER + param)
                .then()
                .log().all();
    }

}
