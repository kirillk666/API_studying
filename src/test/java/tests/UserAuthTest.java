package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAuthTest extends BaseTestCase {

    String cookie;
    String header;
    int userId;

    @BeforeEach
    public void loginUser() {
        /*
        Logs user into the system
        Params:
            @ email : string
            @ password : string
        https://playground.learnqa.ru/api/user/login
        */
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response response = RestAssured
                .given()
                .body(authData)
                .when()
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        assertEquals(200, response.getStatusCode(), "Unexpected status code");

        cookie = getCookie(response, "auth_sid");
        header = getHeader(response, "x-csrf-token");

        userId = getIntFromJson(response, "user_id");
        assertTrue(userId > 0, "User id should be greater then zero");
    }

    @Test
    public void authUser() {
        /*
        Get user id you are authorizes as OR get 0 if not authorized
        https://playground.learnqa.ru/api/user/auth
         */
        Response responseCheckAuth = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .when()
                .get("https://playground.learnqa.ru/api/user/auth")
                .andReturn();

        Assertions.assertIntValFromJsonByName(responseCheckAuth, "user_id", userId);
    }

    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void negativeAuthUser(String condition) {
        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");

        if(condition.equals("cookie")) {
            spec.cookie("auth_sid", cookie);
        } else if(condition.equals("headers")) {
            spec.header("headers", header);
        } else {
            throw new IllegalArgumentException("Condition value '" + condition + "' is not known");
        }

        Response responseForCheck = spec.get().andReturn();
        Assertions.assertIntValFromJsonByName(responseForCheck, "user_id", 0);
    }
}
